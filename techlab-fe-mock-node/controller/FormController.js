import express from 'express';
const router = express.Router();

const apiForm = (req, res) => {
    res.render('payment/form');
};

const playerForm = (req, res) => {
    res.render('players/playerForm');
};

const walletForm = (req, res) => {
    res.render('wallet/walletForm');
};


// Define routes within the controller
router.get("/api/form", apiForm);

router.get("/api/player-form", playerForm);

router.get("/api/wallet-form", walletForm);

export default router;