import visaController from './controller/VisaController.js';
import amazonController from './controller/AmazonController.js';
import paypalController from './controller/PayPalController.js';
import config from './config.js';
import express from 'express';

const app = express();
const port = config.server.port;


app.use(express.json());

app.use(visaController);
app.use(paypalController);
app.use(amazonController);

app.listen(port, () => {
    console.log(`Mock Front-End Microservice is running at http://localhost:${port}`);
});