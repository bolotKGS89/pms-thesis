import express from 'express';
import config from '../config.js';
import axios from 'axios';
const router = express.Router();

const createCheckoutSession = (req, res) => {
  axios.get(`${config.ewalletUrl}/create-checkout-session`)
  .then(response => {
    console.log('Response:', response.data);
    res.send(response.data);
  })
  .catch(error => {
    console.error('Error:', error.message);
    res.send(error.message);
  });
}

const retrieveCheckoutSession = (req, res) => {
  // Use req.params.id to access the id parameter
  const { id } = req.params;

  axios.get(`${config.ewalletUrl}/get-checkout-session/${id}`)
    .then(response => {
      console.log('Response:', response.data);
      res.send(response.data);
    })
    .catch(error => {
      console.error('Error:', error.message);
      res.send(error.message);
    });
}

const completeCheckoutSession = (req, res) => {
  // Use req.params.id to access the id parameter
  const { id } = req.params;

  axios.post(`${config.ewalletUrl}/complete-checkout-session/${id}`)
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
router.get("/api/amazon/create-checkout-session", createCheckoutSession);

router.get("/api/amazon/get-checkout-session/:id", retrieveCheckoutSession);

router.post("/api/amazon/complete-checkout-session/:id", completeCheckoutSession);

export default router;