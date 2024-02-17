import express from 'express';
import config from '../config.js';
import axios from 'axios';
import { v4 } from 'uuid';
const router = express.Router();


const requestHeaders = {
  'X-Request-ID' : v4().toString(),
  'Service-Name' : 'techlab-fe-mock-node'
}

const sendCredit = (req, res) => {
    axios.post(`${config.paymentUrl}/credit`, req.body, {
      headers: requestHeaders
    })
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
    axios.post(`${config.paymentUrl}/debit`, req.body, {
      headers: requestHeaders
    })
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

    axios.get(`${config.ewalletUrl}/${id}`, {
      headers: requestHeaders
    })
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