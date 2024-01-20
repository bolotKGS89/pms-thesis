// config.js
const config = {
    server: {
        port: 3000,
    },
    database: {
        connectionString: process.env.DB_CONNECTION_STRING || 'mongodb://localhost:27017/mydatabase',
    },
    ewalletUrl : "http://localhost:8203/ewl/api/gateway"
// Add more configuration options as needed
};
  
export default config;