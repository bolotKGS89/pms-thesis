// controllers/visaController.js
import express from 'express';
import config from '../config.js';
import axios from 'axios';
import { v4 } from 'uuid';
const router = express.Router();

const requestHeaders = {
  'X-Request-ID' : v4().toString(),
  'Service-Name' : 'techlab-fe-mock-node'
}

const createPayment = (req, res) => {
  axios.post(`${config.ewalletUrl}/create`, req.body, {
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

const authorizePayment = (req, res) => {
  // Use req.params.id to access the id parameter
  const { id } = req.params;
  

  axios.get(`${config.ewalletUrl}/authorize/${id}`, {
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

const capturePayment = (req, res) => {
  // Use req.params.id to access the id parameter
  const { id } = req.params;

  axios.get(`${config.ewalletUrl}/capture/paypal/${id}`, {
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

const refundPayment = (req, res) => {
  axios.post(`${config.ewalletUrl}/refund`, req.body, {
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

const retrievePayment = (req, res) => {
  // Use req.params.id to access the id parameter
  const { id } = req.params;

  axios.get(`${config.ewalletUrl}/retrieve/paypal/${id}`, {
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

// Define routes within the controller
router.post("/api/paypal/create", createPayment);

router.get("/api/paypal/authorize/:id", authorizePayment);

router.get("/api/paypal/capture/:id", capturePayment);

router.post("/api/paypal/refund", refundPayment);

router.get("/api/paypal/retrieve/:id", retrievePayment);

export default router;