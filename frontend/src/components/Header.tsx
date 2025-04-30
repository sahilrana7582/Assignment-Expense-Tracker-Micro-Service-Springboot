import { AppBar, Toolbar, Typography, Button, Box, IconButton, useTheme, useMediaQuery } from '@mui/material';
import { motion } from 'framer-motion';
import { Dashboard, AccountCircle, Menu } from '@mui/icons-material';
import { useState } from 'react';

const Header = () => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('sm'));
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  return (
    <AppBar position="static" elevation={0} sx={{ 
      background: 'rgba(255, 255, 255, 0.8)',
      backdropFilter: 'blur(10px)',
      borderBottom: '1px solid rgba(255, 255, 255, 0.6)',
    }}>
      <Toolbar sx={{ px: { xs: 2, sm: 4, md: 6 } }}>
        <motion.div
          initial={{ opacity: 0, x: -20 }}
          animate={{ opacity: 1, x: 0 }}
          transition={{ duration: 0.5 }}
        >
          <Typography variant="h6" component="div" sx={{ 
            display: 'flex', 
            alignItems: 'center',
            gap: 1,
            color: '#3a7bd5',
            fontWeight: 700
          }}>
            <Dashboard /> ExpenseTracker
          </Typography>
        </motion.div>
        
        <Box sx={{ flexGrow: 1 }} />
        
        {isMobile ? (
          <IconButton 
            color="primary" 
            onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
          >
            <Menu />
          </IconButton>
        ) : (
          <Box sx={{ display: 'flex', gap: 3, alignItems: 'center' }}>
            {['Dashboard', 'Transactions', 'Reports', 'Settings'].map((item, index) => (
              <motion.div
                key={item}
                initial={{ opacity: 0, y: -10 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ delay: index * 0.1 }}
                whileHover={{ y: -3 }}
              >
                <Typography 
                  sx={{ 
                    color: 'text.secondary',
                    fontWeight: 500,
                    cursor: 'pointer',
                    '&:hover': { color: '#3a7bd5' }
                  }}
                >
                  {item}
                </Typography>
              </motion.div>
            ))}
            
            <motion.div
              whileHover={{ scale: 1.05 }}
              whileTap={{ scale: 0.95 }}
            >
              <Button 
                variant="contained" 
                startIcon={<AccountCircle />}
                sx={{ 
                  background: 'linear-gradient(45deg, #3a7bd5 0%, #00d2ff 100%)',
                  borderRadius: '20px',
                  textTransform: 'none',
                  fontWeight: 600,
                  boxShadow: '0 4px 10px rgba(0, 210, 255, 0.3)',
                }}
              >
                Sign In
              </Button>
            </motion.div>
          </Box>
        )}
      </Toolbar>
    </AppBar>
  );
};

export default Header;