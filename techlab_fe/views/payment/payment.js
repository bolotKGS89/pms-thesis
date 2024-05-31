function setType(type) {

  // Show or hide the intent section based on the selected type
  if (type === 'Paypal') {
    document.getElementById('intentSection').style.display = 'block';
  } else {
    document.getElementById('intentSection').style.display = 'none';
  }

  if (type === 'Amazon') {
    document.getElementById('amazonSection').style.display = 'block';
  } else {
    document.getElementById('amazonSection').style.display = 'none';
  }
}

function refundPayment() {
  const form = document.getElementById('paymentForm');
  const formData = new FormData(form);
  const apiUrl = `http://localhost:3000/api/${Object.fromEntries(formData.entries())['type'].toLowerCase()}/refund`;

  // Convert form data to JSON
  const jsonData = {};
  formData.forEach((value, key) => {
    jsonData[key] = value;
  });

  // Use fetch to send a POST request to the API endpoint
  fetch(apiUrl, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(jsonData),
  })
  .then(response => response.json())
  .then(data => {
    alert('Response: ' + JSON.stringify(data));
    location.reload();
  })
  .catch(error => {
    alert('Error:' + error.message);
  });
}

function capturePayment() {
  const form = document.getElementById('paymentForm');
  const formData = new FormData(form);
  const paymentType = Object.fromEntries(formData.entries())['type'].toLowerCase();
  const transactionId = Object.fromEntries(formData.entries())['transactionId'];
  const apiUrl = `http://localhost:3000/api/${paymentType}/capture/${transactionId}`;

  // Use fetch to send a GET request to the API endpoint
  fetch(apiUrl, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  })
  .then(response => response.text())
  .then(data => {
    alert('Response: ' + data);
    location.reload();
  })
  .catch(error => {
    alert('Error:' + error.message);
  });
}

function confirmOrAuthorizePayment() {
  const form = document.getElementById('paymentForm');
  const formData = new FormData(form);
  const paymentType = Object.fromEntries(formData.entries())['type'].toLowerCase();
  const transactionId = Object.fromEntries(formData.entries())['transactionId'];
  let apiUrl;


  if(paymentType === 'visa') {
    apiUrl = `http://localhost:3000/api/${paymentType}/confirm/${transactionId}`
  } else if(paymentType === 'paypal') {
    apiUrl = `http://localhost:3000/api/${paymentType}/authorize/${transactionId}`
  }

  // Use fetch to send a GET request to the API endpoint
  fetch(apiUrl, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  })
  .then(response => response.text())
  .then(data => {
    alert('Response: ' + data);
    location.reload();
  })
  .catch(error => {
    alert('Error:' + error.message);
  });
}

function retrievePayment() {
  const form = document.getElementById('paymentForm');
  const formData = new FormData(form);
  const paymentType = Object.fromEntries(formData.entries())['type'].toLowerCase();
  const transactionId = Object.fromEntries(formData.entries())['transactionId'];
  const apiUrl = `http://localhost:3000/api/${paymentType}/retrieve/${transactionId}`;

  // Use fetch to send a GET request to the API endpoint
  fetch(apiUrl, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  })
    .then(response => response.text())
    .then(data => {
      alert('Response: ' + data);
      location.reload();
    })
    .catch(error => {
      alert('Error:' + error.message);
    });
  }

function createPayment() {
    const form = document.getElementById('paymentForm');
    const formData = new FormData(form);
    const apiUrl = `http://localhost:3000/api/${Object.fromEntries(formData.entries())['type'].toLowerCase()}/create`;
    console.log(apiUrl);

    // Convert form data to JSON
    const jsonData = {};
    formData.forEach((value, key) => {
      jsonData[key] = value;
    });

    // Use fetch to send a POST request to the API endpoint
    fetch(apiUrl, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(jsonData),
    })
    .then(response => response.json())
    .then(data => {
      alert('Response: ' + JSON.stringify(data));
      location.reload();
    })
    .catch(error => {
      alert('Error:' + error.message);
    });
  }

function createSession() {
  const form = document.getElementById('paymentForm');
  const formData = new FormData(form);
  const paymentType = Object.fromEntries(formData.entries())['type'].toLowerCase();
  const apiUrl = `http://localhost:3000/api/${paymentType}/create-checkout-session`;

  // Use fetch to send a GET request to the API endpoint
  fetch(apiUrl, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  })
    .then(response => response.text())
    .then(data => {
      alert('Response: ' + data);
      location.reload();
    })
    .catch(error => {
      alert('Error:' + error.message);
    });
  }

  function completeSession() {
    const form = document.getElementById('paymentForm');
    const formData = new FormData(form);
    const paymentType = Object.fromEntries(formData.entries())['type'].toLowerCase();
    const transactionId = Object.fromEntries(formData.entries())['transactionId'];
    const apiUrl = `http://localhost:3000/api/${paymentType}/complete-checkout-session/${transactionId}`;
  
    // Use fetch to send a GET request to the API endpoint
    fetch(apiUrl, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    })
      .then(response => response.text())
      .then(data => {
        alert('Response: ' + data);
        location.reload();
      })
      .catch(error => {
        alert('Error:' + error.message);
      });
    }