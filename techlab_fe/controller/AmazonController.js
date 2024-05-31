import express from 'express';
import config from '../config.js';
import axios from 'axios';
import { v4 } from 'uuid';
import log4js from 'log4js';
import request from 'superagent';

const router = express.Router();
log4js.configure({
  appenders: {
    logstash: {
      type: 'log4js-logstash-tcp',
      host: 'logstash',
      port: 5000
    },
  },
  categories: {
    default: {
      appenders: ['logstash'],
      level: 'info',
    },
  },
});
// Get a logger instance
const logger = log4js.getLogger();

// Set timeout to 2000 milliseconds (2 seconds)
const timeoutInMilliseconds = 2000;

const requestHeaders = {
  'X-Request-ID' : v4().toString(),
  'Service-Name' : 'fe'
}

const createCheckoutSession = (req, res) => {


  logger.info(`Sending message to ewallet (request_id: ${requestHeaders['X-Request-ID']})`)
  request
  .get(`${config.ewalletUrl}/create-checkout-session`)
  .send(req.body)
  .set(requestHeaders)
  .timeout({ response: timeoutInMilliseconds })
  .then(response => {
    logger.info(`Receiving answer from ${response.body['serviceName']} (request_id: ${response.body['requestId']})`);
    res.send(response.body);
  })
  .catch(error => {
    console.log(error.response)
    logger.error(`Error response (code: 500) received from ewallet (request_id: undefined})`);
    res.status(500).send('Error');
  });
  
  // logger.info(`Sending message to techlab_ewallet (request_id: [${requestHeaders['X-Request-ID']}])`)
  // axios.get(`${config.ewalletUrl}/create-checkout-session`, {
  //   headers: requestHeaders
  // })
  // .then(response => {
  //   logger.info(`Receiving answer from techlab_ewallet (request_id: [${requestHeaders['X-Request-ID']}])`)
  //   res.send(response.data);
  // })
  // .catch(error => {
  //   if (error.code === 'ERR_BAD_RESPONSE') {
  //     if(error.response) {
  //       logger.error(`Failing to contact ${error.response.data['serviceName']} (request_id: [${requestHeaders['X-Request-ID']}]). Root cause: ${error.response.data['message']}`)
  //       res.send(error.response.data);
  //     }    
  //   } else {
  //     if(error.response) {
  //       logger.error(`Error response (code: 500) received from ${error.response.data['serviceName']} (request_id: [${requestHeaders['X-Request-ID']}])`)
  //       res.send(error.response.data);
  //     }
  //   }     
  // });
}

const retrieveCheckoutSession = (req, res) => {
  // Use req.params.id to access the id parameter
  const { id } = req.params;
  // const requestHeaders = {
  //   'X-Request-ID' : v4().toString(),
  //   'Service-Name' : 'techlab-amazon-develop'
  // }
  
  logger.info(`Sending message to techlab_ewallet (request_id: [${requestHeaders['X-Request-ID']}])`)
  axios.get(`${config.ewalletUrl}/get-checkout-session/${id}`, {
    headers: requestHeaders
  })
  .then(response => {
    logger.info(`Receiving answer from techlab_ewallet (request_id: [${requestHeaders['X-Request-ID']}])`)
    res.send(response.data);
  })
  .catch(error => {
    if (error.code === 'ERR_BAD_RESPONSE') {
      if(error.response) {
        logger.error(`Failing to contact ${error.response.data['serviceName']} (request_id: [${requestHeaders['X-Request-ID']}]). Root cause: ${error.response.data['message']}`)
        res.send(error.response.data);
      }    
    } else {
      if(error.response) {
        logger.error(`Error response (code: 500) received from ${error.response.data['serviceName']} (request_id: [${requestHeaders['X-Request-ID']}])`)
        res.send(error.response.data);
      }
    }     
  });
}

const completeCheckoutSession = (req, res) => {
  // Use req.params.id to access the id parameter
  const { id } = req.params;
  const requestHeaders = {
    'X-Request-ID' : v4().toString(),
    'Service-Name' : 'techlab-amazon-develop'
  }
  
  logger.info(`Sending message to techlab_ewallet (request_id: [${requestHeaders['X-Request-ID']}])`)
  axios.post(`${config.ewalletUrl}/complete-checkout-session/${id}`, {
    headers: requestHeaders
  })
  .then(response => {
    logger.info(`Receiving answer from techlab_ewallet (request_id: [${requestHeaders['X-Request-ID']}])`)
    res.send(response.data);
  })
  .catch(error => {
    if (error.code === 'ERR_BAD_RESPONSE') {
      if(error.response) {
        logger.error(`Failing to contact ${error.response.data['serviceName']} (request_id: [${requestHeaders['X-Request-ID']}]). Root cause: ${error.response.data['message']}`)
        res.send(error.response.data);
      }    
    } else {
      if(error.response) {
        logger.error(`Error response (code: 500) received from ${error.response.data['serviceName']} (request_id: [${requestHeaders['X-Request-ID']}])`)
        res.send(error.response.data);
      }
    }     
  });
}

// Define routes within the controller
router.get("/api/amazon/create-checkout-session", createCheckoutSession);

router.get("/api/amazon/get-checkout-session/:id", retrieveCheckoutSession);

router.post("/api/amazon/complete-checkout-session/:id", completeCheckoutSession);

export default router;