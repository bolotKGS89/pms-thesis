import express from 'express';
import config from '../config.js';
import axios from 'axios';
const router = express.Router();

const sendCredit = (req, res) => {
    axios.post(`${config.paymentUrl}/credit`, req.body)
    .then(response => {
      console.log('Response:', response.data);
      res.send(response.data);
    })
    .catch(error => {
      console.error('Error:', error.message);
      res.send(error.message);
    });
}

const sendDebit = (req, res) => {
    axios.post(`${config.paymentUrl}/debit`, req.body)
    .then(response => {
      console.log('Response:', response.data);
      res.send(response.data);
    })
    .catch(error => {
      console.error('Error:', error.message);
      res.send(error.message);
    });
}  

const retrieve = (req, res) => {
    // Use req.params.id to access the id parameter
    const { id } = req.params;

    axios.get(`${config.ewalletUrl}/${id}`)
    .then(response => {
      console.log('Response:', response.data);
      res.send(JSON.parse(response.data));
    })
    .catch(error => {
      console.error('Error:', error.message);
      res.send(error.message);
    });
}  
// Define routes within the controller
router.post("/api/wallet/credit", sendCredit);

router.post("/api/wallet/debit", sendDebit);

router.get("/api/wallet/retrieve/:id", retrieve);

export default router;