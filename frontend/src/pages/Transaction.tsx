import React, { useState, useEffect } from 'react';
import { 
  Box, 
  Typography, 
  Paper, 
  TextField, 
  Button, 
  Select, 
  MenuItem, 
  FormControl, 
  InputLabel, 
  Grid, 
  Divider, 
  Chip, 
  useTheme, 
  useMediaQuery,
  SelectChangeEvent,
  InputAdornment,
  CircularProgress,
  Snackbar,
  Alert,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TablePagination,
  IconButton,
  Tooltip
} from '@mui/material';
import { 
  Send, 
  CheckCircleOutline, 
  Category, 
  AccountBalance, 
  Description, 
  AttachMoney, 
  SwapHoriz,
  ErrorOutline,
  Refresh,
  ArrowUpward,
  ArrowDownward,
  Payment
} from '@mui/icons-material';
import { motion } from 'framer-motion';
import Header from '../components/Header';
import Footer from '../components/Footer';

// Transaction types and categories
const transactionTypes = ['DEPOSIT', 'WITHDRAWAL', 'TRANSFER', 'PAYMENT'];
const transactionCategories = ['FOOD', 'TRANSPORT', 'ENTERTAINMENT', 'UTILITIES', 'SHOPPING', 'HEALTH', 'EDUCATION', 'INVESTMENT', 'OTHER'];

// Interface for transaction data
interface TransactionData {
  userId: string;
  accountId: string;
  amount: number;
  currency: string;
  type: string;
  category: string;
  status: string;
  description: string;
}

// Interface for transaction response
interface TransactionResponse extends TransactionData {
  transactionId: string;
}

// Interface for pageable response
interface PageableResponse {
  content: TransactionResponse[];
  pageable: {
    pageNumber: number;
    pageSize: number;
  };
  totalElements: number;
}

const Transaction = () => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('sm'));
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);
  const [error, setError] = useState<string | null>(null);

  // State for transactions table
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [transactions, setTransactions] = useState<TransactionResponse[]>([]);
  const [totalElements, setTotalElements] = useState(0);
  const [loadingTransactions, setLoadingTransactions] = useState(false);
  
  // State for transaction form
  const [transaction, setTransaction] = useState<TransactionData>({
    userId: "1234", // Using the provided userId
    accountId: "xybsdeds",
    amount: 0,
    currency: "INR",
    type: "TRANSFER",
    category: "INVESTMENT",
    status: "PENDING",
    description: ""
  });
  
  // State for transaction response
  const [response, setResponse] = useState<TransactionResponse | null>(null);
  
  // Function to fetch transactions
  const fetchTransactions = async () => {
    setLoadingTransactions(true);
    try {
      const response = await fetch(
        `http://localhost:8084/api/v1/transactions/user/1234?page=${page}&size=${rowsPerPage}`
      );
      if (!response.ok) {
        throw new Error('Failed to fetch transactions');
      }
      const data: PageableResponse = await response.json();
      setTransactions(data.content);
      setTotalElements(data.totalElements);
    } catch (err) {
      console.error('Error fetching transactions:', err);
      setError('Failed to load transactions. Please try again.');
    } finally {
      setLoadingTransactions(false);
    }
  };

  // Fetch transactions on mount and when page/rowsPerPage changes
  useEffect(() => {
    fetchTransactions();
  }, [page, rowsPerPage]);
  
  // Handle form input changes
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setTransaction(prev => ({
      ...prev,
      [name]: name === 'amount' ? parseFloat(value) || 0 : value
    }));
  };
  
  // Handle select changes
  const handleSelectChange = (e: SelectChangeEvent) => {
    const { name, value } = e.target;
    setTransaction(prev => ({
      ...prev,
      [name]: value
    }));
  };
  
  // Handle form submission
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    
    try {
      // Call the actual API endpoint
      const response = await fetch('http://localhost:8084/api/v1/transactions', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(transaction),
      });
      
      if (!response.ok) {
        throw new Error(`Error: ${response.status} - ${response.statusText}`);
      }
      
      const data = await response.json();
      setResponse(data);
      setSuccess(true);
      
      // Refresh the transaction list after adding a new one
      fetchTransactions();
    } catch (err) {
      console.error('Error creating transaction:', err);
      setError(err instanceof Error ? err.message : "Failed to create transaction. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  // Handlers for pagination
  const handleChangePage = (event: unknown, newPage: number) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  // Function to get transaction type icon
  const getTransactionTypeIcon = (type: string) => {
    switch (type) {
      case 'DEPOSIT':
        return <ArrowUpward color="success" />;
      case 'WITHDRAWAL':
        return <ArrowDownward color="error" />;
      case 'TRANSFER':
        return <SwapHoriz color="info" />;
      case 'PAYMENT':
        return <Payment color="warning" />;
      default:
        return null;
    }
  };
  
  // Animation variants
  const containerVariants = {
    hidden: { opacity: 0 },
    visible: {
      opacity: 1,
      transition: {
        staggerChildren: 0.1
      }
    }
  };
  
  const itemVariants = {
    hidden: { opacity: 0, y: 20 },
    visible: {
      opacity: 1,
      y: 0,
      transition: {
        type: "spring",
        stiffness: 100
      }
    }
  };

  return (
    <Box sx={{ 
      minHeight: '100vh',
      display: 'flex',
      flexDirection: 'column',
      background: 'linear-gradient(135deg, #f6f9fc 0%, #dfe9f3 100%)',
      width: '100%',
      overflow: 'hidden'
    }}>
      <Header />
      
      <Box 
        component={motion.div}
        initial="hidden"
        animate="visible"
        variants={containerVariants}
        sx={{ 
          flex: 1, 
          display: 'flex',
          flexDirection: 'column',
          width: '100%',
          px: { xs: 2, sm: 4, md: 6 },
          py: { xs: 4, sm: 5, md: 6 }
        }}
      >
        {/* Page Title */}
        <Box 
          component={motion.div}
          variants={itemVariants}
          sx={{ 
            textAlign: 'center',
            mb: { xs: 4, md: 6 }
          }}
        >
          <Typography 
            variant={isMobile ? "h4" : "h3"} 
            component="h1" 
            sx={{
              fontWeight: 800,
              background: 'linear-gradient(45deg, #3a7bd5 0%, #00d2ff 100%)',
              backgroundClip: 'text',
              textFillColor: 'transparent',
              WebkitBackgroundClip: 'text',
              WebkitTextFillColor: 'transparent',
              mb: 2,
              letterSpacing: '-0.5px'
            }}
          >
            Create Transaction
          </Typography>
          <Typography 
            variant="body1" 
            sx={{ 
              color: 'text.secondary',
              maxWidth: '700px',
              mx: 'auto',
              lineHeight: 1.6
            }}
          >
            Create a new transaction to track your expenses, income, or transfers between accounts.
          </Typography>
        </Box>
        
        <Grid container spacing={4}>
          {/* Transaction Form */}
          <Grid item xs={12} md={6}>
            <motion.div variants={itemVariants}>
              <Paper
                elevation={4}
                component="form"
                onSubmit={handleSubmit}
                sx={{
                  p: { xs: 3, md: 4 },
                  borderRadius: '16px',
                  background: 'rgba(255, 255, 255, 0.8)',
                  backdropFilter: 'blur(10px)',
                  border: '1px solid rgba(255, 255, 255, 0.6)',
                  height: '100%'
                }}
              >
                <Typography variant="h6" sx={{ mb: 3, fontWeight: 600 }}>
                  Transaction Details
                </Typography>
                
                <Grid container spacing={3}>
                  <Grid item xs={12}>
                    <TextField
                      fullWidth
                      label="Amount"
                      name="amount"
                      type="number"
                      value={transaction.amount}
                      onChange={handleChange}
                      required
                      InputProps={{
                        startAdornment: (
                          <InputAdornment position="start">
                            <AttachMoney />
                          </InputAdornment>
                        ),
                      }}
                      sx={{
                        '& .MuiOutlinedInput-root': {
                          borderRadius: '12px',
                        }
                      }}
                    />
                  </Grid>
                  
                  <Grid item xs={12} sm={6}>
                    <FormControl fullWidth>
                      <InputLabel id="currency-label">Currency</InputLabel>
                      <Select
                        labelId="currency-label"
                        name="currency"
                        value={transaction.currency}
                        label="Currency"
                        onChange={handleSelectChange}
                        sx={{ borderRadius: '12px' }}
                        startAdornment={
                          <InputAdornment position="start" sx={{ ml: 1 }}>
                            <SwapHoriz />
                          </InputAdornment>
                        }
                      >
                        <MenuItem value="USD">USD</MenuItem>
                        <MenuItem value="EUR">EUR</MenuItem>
                        <MenuItem value="GBP">GBP</MenuItem>
                        <MenuItem value="INR">INR</MenuItem>
                        <MenuItem value="JPY">JPY</MenuItem>
                      </Select>
                    </FormControl>
                  </Grid>
                  
                  <Grid item xs={12} sm={6}>
                    <FormControl fullWidth>
                      <InputLabel id="type-label">Type</InputLabel>
                      <Select
                        labelId="type-label"
                        name="type"
                        value={transaction.type}
                        label="Type"
                        onChange={handleSelectChange}
                        sx={{ borderRadius: '12px' }}
                        startAdornment={
                          <InputAdornment position="start" sx={{ ml: 1 }}>
                            <AccountBalance />
                          </InputAdornment>
                        }
                      >
                        {transactionTypes.map(type => (
                          <MenuItem key={type} value={type}>{type}</MenuItem>
                        ))}
                      </Select>
                    </FormControl>
                  </Grid>
                  
                  <Grid item xs={12}>
                    <FormControl fullWidth>
                      <InputLabel id="category-label">Category</InputLabel>
                      <Select
                        labelId="category-label"
                        name="category"
                        value={transaction.category}
                        label="Category"
                        onChange={handleSelectChange}
                        sx={{ borderRadius: '12px' }}
                        startAdornment={
                          <InputAdornment position="start" sx={{ ml: 1 }}>
                            <Category />
                          </InputAdornment>
                        }
                      >
                        {transactionCategories.map(category => (
                          <MenuItem key={category} value={category}>{category}</MenuItem>
                        ))}
                      </Select>
                    </FormControl>
                  </Grid>
                  
                  <Grid item xs={12}>
                    <TextField
                      fullWidth
                      label="Description"
                      name="description"
                      value={transaction.description}
                      onChange={handleChange}
                      multiline
                      rows={3}
                      placeholder="Enter transaction details..."
                      InputProps={{
                        startAdornment: (
                          <InputAdornment position="start">
                            <Description />
                          </InputAdornment>
                        ),
                      }}
                      sx={{
                        '& .MuiOutlinedInput-root': {
                          borderRadius: '12px',
                        }
                      }}
                    />
                  </Grid>
                  
                  <Grid item xs={12}>
                    <Button
                      type="submit"
                      variant="contained"
                      fullWidth
                      disabled={loading}
                      startIcon={loading ? <CircularProgress size={20} color="inherit" /> : <Send />}
                      sx={{
                        background: 'linear-gradient(45deg, #3a7bd5 0%, #00d2ff 100%)',
                        boxShadow: '0 8px 20px rgba(0, 210, 255, 0.3)',
                        color: 'white',
                        padding: '12px',
                        borderRadius: '12px',
                        fontSize: '1rem',
                        textTransform: 'none',
                        fontWeight: 600,
                        '&:hover': {
                          boxShadow: '0 12px 25px rgba(0, 210, 255, 0.4)'
                        }
                      }}
                    >
                      {loading ? 'Processing...' : 'Create Transaction'}
                    </Button>
                  </Grid>
                </Grid>
              </Paper>
            </motion.div>
          </Grid>
          
          {/* Transaction Response */}
          <Grid item xs={12} md={6}>
            <motion.div variants={itemVariants}>
              <Paper
                elevation={4}
                sx={{
                  p: { xs: 3, md: 4 },
                  borderRadius: '16px',
                  background: 'rgba(255, 255, 255, 0.8)',
                  backdropFilter: 'blur(10px)',
                  border: '1px solid rgba(255, 255, 255, 0.6)',
                  height: '100%',
                  display: 'flex',
                  flexDirection: 'column',
                  justifyContent: response ? 'flex-start' : 'center',
                  alignItems: 'center'
                }}
              >
                {response ? (
                  <Box sx={{ width: '100%' }}>
                    <Box sx={{ 
                      display: 'flex', 
                      alignItems: 'center', 
                      justifyContent: 'space-between',
                      mb: 3
                    }}>
                      <Typography variant="h6" sx={{ fontWeight: 600 }}>
                        Transaction Result
                      </Typography>
                      <Chip 
                        icon={<CheckCircleOutline />} 
                        label="Success" 
                        color="success" 
                        variant="outlined"
                      />
                    </Box>
                    
                    <Divider sx={{ mb: 3 }} />
                    
                    <Grid container spacing={2}>
                      <Grid item xs={12}>
                        <Box sx={{ 
                          p: 2, 
                          bgcolor: 'rgba(58, 123, 213, 0.1)', 
                          borderRadius: '8px',
                          mb: 2
                        }}>
                          <Typography variant="subtitle2" color="text.secondary">
                            Transaction ID
                          </Typography>
                          <Typography variant="body1" sx={{ fontWeight: 500 }}>
                            {response.transactionId}
                          </Typography>
                        </Box>
                      </Grid>
                      
                      <Grid item xs={6}>
                        <Typography variant="subtitle2" color="text.secondary">
                          Amount
                        </Typography>
                        <Typography variant="h6" sx={{ fontWeight: 600, color: '#3a7bd5' }}>
                          {response.amount} {response.currency}
                        </Typography>
                      </Grid>
                      
                      <Grid item xs={6}>
                        <Typography variant="subtitle2" color="text.secondary">
                          Status
                        </Typography>
                        <Typography variant="body1" sx={{ 
                          fontWeight: 600,
                          color: response.status === 'COMPLETED' ? 'success.main' : 'warning.main'
                        }}>
                          {response.status}
                        </Typography>
                      </Grid>
                      
                      <Grid item xs={6}>
                        <Typography variant="subtitle2" color="text.secondary">
                          Type
                        </Typography>
                        <Typography variant="body1">
                          {response.type}
                        </Typography>
                      </Grid>
                      
                      <Grid item xs={6}>
                        <Typography variant="subtitle2" color="text.secondary">
                          Category
                        </Typography>
                        <Typography variant="body1">
                          {response.category}
                        </Typography>
                      </Grid>
                      
                      {response.description && (
                        <Grid item xs={12}>
                          <Typography variant="subtitle2" color="text.secondary">
                            Description
                          </Typography>
                          <Typography variant="body1">
                            {response.description}
                          </Typography>
                        </Grid>
                      )}
                    </Grid>
                    
                    <Box sx={{ mt: 4, display: 'flex', justifyContent: 'center' }}>
                      <Button
                        variant="outlined"
                        onClick={() => setResponse(null)}
                        sx={{
                          borderRadius: '12px',
                          textTransform: 'none',
                          borderColor: '#3a7bd5',
                          color: '#3a7bd5',
                          '&:hover': {
                            borderColor: '#3a7bd5',
                            backgroundColor: 'rgba(58, 123, 213, 0.1)'
                          }
                        }}
                      >
                        Create Another Transaction
                      </Button>
                    </Box>
                  </Box>
                ) : (
                  <Box sx={{ 
                    textAlign: 'center', 
                    p: 3,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                    justifyContent: 'center',
                    height: '100%'
                  }}>
                    <Box 
                      component={motion.div}
                      animate={{ 
                        y: [0, -10, 0],
                        transition: { 
                          repeat: Infinity, 
                          duration: 3,
                          ease: "easeInOut"
                        }
                      }}
                      sx={{ 
                        color: '#3a7bd5',
                        fontSize: '5rem',
                        mb: 3
                      }}
                    >
                      <SwapHoriz fontSize="inherit" />
                    </Box>
                    <Typography variant="h6" sx={{ mb: 2, fontWeight: 600 }}>
                      Transaction Preview
                    </Typography>
                    <Typography variant="body1" color="text.secondary">
                      Fill out the form to create a new transaction and see the result here.
                    </Typography>
                  </Box>
                )}
              </Paper>
            </motion.div>
          </Grid>
        </Grid>

        {/* Transaction History Table */}
        <Box sx={{ mt: 6 }}>
          <Paper
            elevation={4}
            sx={{
              p: { xs: 2, md: 3 },
              borderRadius: '16px',
              background: 'rgba(255, 255, 255, 0.8)',
              backdropFilter: 'blur(10px)',
              border: '1px solid rgba(255, 255, 255, 0.6)',
            }}
          >
            <Box sx={{ 
              display: 'flex', 
              justifyContent: 'space-between', 
              alignItems: 'center',
              mb: 3 
            }}>
              <Typography variant="h6" sx={{ fontWeight: 600 }}>
                Transaction History
              </Typography>
              <Tooltip title="Refresh transactions">
                <IconButton 
                  onClick={() => fetchTransactions()}
                  disabled={loadingTransactions}
                >
                  <Refresh />
                </IconButton>
              </Tooltip>
            </Box>

            <TableContainer>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>Type</TableCell>
                    <TableCell>Amount</TableCell>
                    <TableCell>Category</TableCell>
                    <TableCell>Status</TableCell>
                    <TableCell>Description</TableCell>
                    <TableCell>Transaction ID</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {loadingTransactions ? (
                    <TableRow>
                      <TableCell colSpan={6} align="center" sx={{ py: 3 }}>
                        <CircularProgress size={24} />
                      </TableCell>
                    </TableRow>
                  ) : transactions.length === 0 ? (
                    <TableRow>
                      <TableCell colSpan={6} align="center" sx={{ py: 3 }}>
                        No transactions found
                      </TableCell>
                    </TableRow>
                  ) : (
                    transactions.map((transaction) => (
                      <TableRow key={transaction.transactionId}>
                        <TableCell>
                          <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                            {getTransactionTypeIcon(transaction.type)}
                            {transaction.type}
                          </Box>
                        </TableCell>
                        <TableCell>
                          <Typography
                            sx={{
                              color: transaction.type === 'WITHDRAWAL' ? 'error.main' : 'success.main',
                              fontWeight: 600
                            }}
                          >
                            {transaction.type === 'WITHDRAWAL' ? '-' : '+'}
                            {transaction.amount} {transaction.currency}
                          </Typography>
                        </TableCell>
                        <TableCell>
                          <Chip 
                            label={transaction.category} 
                            size="small"
                            sx={{ 
                              backgroundColor: 'rgba(58, 123, 213, 0.1)',
                              color: '#3a7bd5'
                            }} 
                          />
                        </TableCell>
                        <TableCell>
                          <Chip
                            label={transaction.status}
                            size="small"
                            color={transaction.status === 'COMPLETED' ? 'success' : 'warning'}
                            variant="outlined"
                          />
                        </TableCell>
                        <TableCell>{transaction.description}</TableCell>
                        <TableCell>
                          <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                            {transaction.transactionId.slice(0, 8)}...
                          </Typography>
                        </TableCell>
                      </TableRow>
                    ))
                  )}
                </TableBody>
              </Table>
            </TableContainer>

            <TablePagination
              component="div"
              count={totalElements}
              page={page}
              onPageChange={handleChangePage}
              rowsPerPage={rowsPerPage}
              onRowsPerPageChange={handleChangeRowsPerPage}
              rowsPerPageOptions={[5, 10, 25]}
            />
          </Paper>
        </Box>
      </Box>
      
      <Footer />
      
      {/* Success Snackbar */}
      <Snackbar 
        open={success} 
        autoHideDuration={6000} 
        onClose={() => setSuccess(false)}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
      >
        <Alert 
          onClose={() => setSuccess(false)} 
          severity="success" 
          sx={{ width: '100%', borderRadius: '12px' }}
        >
          Transaction created successfully!
        </Alert>
      </Snackbar>
      
      {/* Error Snackbar */}
      <Snackbar 
        open={!!error} 
        autoHideDuration={6000} 
        onClose={() => setError(null)}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
      >
        <Alert 
          onClose={() => setError(null)} 
          severity="error" 
          sx={{ width: '100%', borderRadius: '12px' }}
        >
          {error}
        </Alert>
      </Snackbar>
    </Box>
  );
};

export default Transaction;