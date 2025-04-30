import { Box, Container, Typography, Link, IconButton, Grid, useTheme, useMediaQuery } from '@mui/material';
import { GitHub, LinkedIn, Twitter, Instagram } from '@mui/icons-material';
import { motion } from 'framer-motion';

const Footer = () => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('sm'));

  return (
    <Box
      component="footer"
      sx={{
        py: { xs: 4, md: 5 },
        px: { xs: 2, sm: 4, md: 6 },
        background: 'rgba(255, 255, 255, 0.8)',
        backdropFilter: 'blur(10px)',
        borderTop: '1px solid rgba(255, 255, 255, 0.6)',
      }}
    >
      <Container maxWidth="lg">
        <Grid container spacing={4}>
          <Grid item xs={12} md={4}>
            <Typography variant="h6" sx={{ fontWeight: 700, color: '#3a7bd5', mb: 2 }}>
              ExpenseTracker
            </Typography>
            <Typography variant="body2" color="text.secondary" sx={{ mb: 2, maxWidth: '300px' }}>
              The smart way to manage your finances. Track expenses, analyze spending patterns, and achieve your financial goals.
            </Typography>
          </Grid>
          
          {!isMobile && (
            <>
              <Grid item xs={12} md={2}>
                <Typography variant="subtitle1" sx={{ fontWeight: 600, mb: 2 }}>
                  Features
                </Typography>
                {['Dashboard', 'Analytics', 'Budgeting', 'Reports'].map(item => (
                  <Typography 
                    key={item} 
                    variant="body2" 
                    color="text.secondary" 
                    sx={{ 
                      mb: 1.5, 
                      cursor: 'pointer',
                      '&:hover': { color: '#3a7bd5' }
                    }}
                  >
                    {item}
                  </Typography>
                ))}
              </Grid>
              
              <Grid item xs={12} md={2}>
                <Typography variant="subtitle1" sx={{ fontWeight: 600, mb: 2 }}>
                  Company
                </Typography>
                {['About Us', 'Careers', 'Blog', 'Contact'].map(item => (
                  <Typography 
                    key={item} 
                    variant="body2" 
                    color="text.secondary" 
                    sx={{ 
                      mb: 1.5, 
                      cursor: 'pointer',
                      '&:hover': { color: '#3a7bd5' }
                    }}
                  >
                    {item}
                  </Typography>
                ))}
              </Grid>
            </>
          )}
          
          <Grid item xs={12} md={4}>
            <Typography variant="subtitle1" sx={{ fontWeight: 600, mb: 2 }}>
              Connect with us
            </Typography>
            <Box sx={{ display: 'flex', gap: 1 }}>
              {[
                { icon: <GitHub />, label: 'GitHub' },
                { icon: <LinkedIn />, label: 'LinkedIn' },
                { icon: <Twitter />, label: 'Twitter' },
                { icon: <Instagram />, label: 'Instagram' }
              ].map((social) => (
                <motion.div
                  key={social.label}
                  whileHover={{ scale: 1.2, rotate: 5 }}
                  whileTap={{ scale: 0.9 }}
                >
                  <IconButton
                    sx={{
                      color: '#3a7bd5',
                      backgroundColor: 'rgba(58, 123, 213, 0.1)',
                      '&:hover': {
                        backgroundColor: 'rgba(58, 123, 213, 0.2)'
                      }
                    }}
                  >
                    {social.icon}
                  </IconButton>
                </motion.div>
              ))}
            </Box>
          </Grid>
        </Grid>
        
        <Box
          sx={{
            mt: 4,
            pt: 3,
            borderTop: '1px solid rgba(0, 0, 0, 0.05)',
            display: 'flex',
            flexDirection: { xs: 'column', sm: 'row' },
            justifyContent: 'space-between',
            alignItems: { xs: 'center', sm: 'center' },
            gap: 2
          }}
        >
          <Typography variant="body2" color="text.secondary">
            © {new Date().getFullYear()} ExpenseTracker. All rights reserved.
          </Typography>
          
          <Box sx={{ display: 'flex', gap: 3 }}>
            {['Privacy Policy', 'Terms of Service', 'Cookies'].map((item) => (
              <Typography 
                key={item}
                variant="body2" 
                color="text.secondary"
                sx={{ 
                  cursor: 'pointer',
                  '&:hover': { color: '#3a7bd5' }
                }}
              >
                {item}
              </Typography>
            ))}
          </Box>
        </Box>
      </Container>
    </Box>
  );
};

export default Footer;