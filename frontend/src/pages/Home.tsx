import { Box, Typography, Button, Grid, Paper, useTheme, useMediaQuery } from '@mui/material';
import { motion } from 'framer-motion';
import { AccountBalance, TrendingUp, PieChart, AddCircle, Dashboard } from '@mui/icons-material';
import Header from '../components/Header';
import Footer from '../components/Footer';

const Home = () => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('sm'));
  
  const containerVariants = {
    hidden: { opacity: 0 },
    visible: {
      opacity: 1,
      transition: {
        staggerChildren: 0.2
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

  const cardHoverVariants = {
    hover: {
      y: -15,
      boxShadow: "0px 15px 30px rgba(0, 0, 0, 0.15)",
      transition: {
        type: "spring",
        stiffness: 300
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
          justifyContent: 'center',
          width: '100%',
          px: { xs: 2, sm: 4, md: 6 },
          py: { xs: 4, sm: 5, md: 6 }
        }}
      >
        {/* Hero Section */}
        <Box 
          component={motion.div}
          variants={itemVariants}
          sx={{ 
            textAlign: 'center',
            mb: { xs: 5, md: 8 }
          }}
        >
          <Typography 
            variant={isMobile ? "h3" : "h2"} 
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
            Smart Expense Tracker
          </Typography>
          <Typography 
            variant={isMobile ? "body1" : "h6"} 
            sx={{ 
              color: 'text.secondary',
              maxWidth: '800px',
              mx: 'auto',
              lineHeight: 1.6,
              mb: 4
            }}
          >
            Visualize your spending habits, set budgets, and achieve your financial goals with our intuitive expense tracking solution.
          </Typography>
        </Box>

        {/* Feature Cards */}
        <Grid container spacing={3} sx={{ mb: { xs: 5, md: 8 } }}>
          {[
            { 
              icon: <Dashboard sx={{ fontSize: isMobile ? 30 : 40 }} />, 
              title: 'Dashboard', 
              description: 'Get a complete overview of your finances at a glance',
              color: '#3a7bd5' 
            },
            { 
              icon: <PieChart sx={{ fontSize: isMobile ? 30 : 40 }} />, 
              title: 'Analytics', 
              description: 'Visualize spending patterns with interactive charts',
              color: '#00d2ff' 
            },
            { 
              icon: <TrendingUp sx={{ fontSize: isMobile ? 30 : 40 }} />, 
              title: 'Budgeting', 
              description: 'Set and track budgets to reach your financial goals',
              color: '#3f51b5' 
            }
          ].map((item, index) => (
            <Grid item xs={12} md={4} key={index}>
              <motion.div 
                variants={itemVariants}
                whileHover="hover"
                initial="rest"
                style={{ height: '100%' }}
              >
                <Paper
                  component={motion.div}
                  variants={cardHoverVariants}
                  elevation={4}
                  sx={{
                    p: { xs: 3, md: 4 },
                    height: '100%',
                    borderRadius: '16px',
                    background: 'rgba(255, 255, 255, 0.8)',
                    backdropFilter: 'blur(10px)',
                    border: '1px solid rgba(255, 255, 255, 0.6)',
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'flex-start'
                  }}
                >
                  <Box sx={{ 
                    color: 'white',
                    backgroundColor: item.color,
                    p: 2,
                    borderRadius: '12px',
                    mb: 3,
                    boxShadow: '0px 4px 10px rgba(0, 0, 0, 0.1)'
                  }}>
                    {item.icon}
                  </Box>
                  <Typography variant="h6" component="h2" sx={{ 
                    fontWeight: 600,
                    mb: 1.5
                  }}>
                    {item.title}
                  </Typography>
                  <Typography variant="body1" color="text.secondary" sx={{ 
                    lineHeight: 1.6
                  }}>
                    {item.description}
                  </Typography>
                </Paper>
              </motion.div>
            </Grid>
          ))}
        </Grid>

        {/* CTA Button */}
        <Box 
          component={motion.div}
          variants={itemVariants}
          sx={{ 
            display: 'flex', 
            justifyContent: 'center',
            mt: { xs: 2, md: 4 }
          }}
        >
          <motion.div
            whileHover={{ scale: 1.05 }}
            whileTap={{ scale: 0.95 }}
          >
            <Button
              variant="contained"
              size="large"
              startIcon={<AddCircle />}
              sx={{
                background: 'linear-gradient(45deg, #3a7bd5 0%, #00d2ff 100%)',
                boxShadow: '0 8px 20px rgba(0, 210, 255, 0.3)',
                color: 'white',
                padding: { xs: '12px 24px', md: '16px 40px' },
                borderRadius: '30px',
                fontSize: { xs: '1rem', md: '1.1rem' },
                textTransform: 'none',
                fontWeight: 600,
                '&:hover': {
                  boxShadow: '0 12px 25px rgba(0, 210, 255, 0.4)'
                }
              }}
            >
              Get Started
            </Button>
          </motion.div>
        </Box>
      </Box>
      <Footer />
    </Box>
  );
};

export default Home;