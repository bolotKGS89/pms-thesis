function credit() {
    const form = document.getElementById('walletForm');
    const formData = new FormData(form);
    const apiUrl = `http://localhost:3000/api/wallet/credit`;

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

function debit() {
    const form = document.getElementById('walletForm');
    const formData = new FormData(form);
    const apiUrl = `http://localhost:3000/api/wallet/debit`;

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

function retrieve() {
    const form = document.getElementById('walletForm');
    const formData = new FormData(form);
    const walletId = Object.fromEntries(formData.entries())['walletId'];
    const apiUrl = `http://localhost:3000/api/wallet/retrieve/${walletId}`;
    console.log(apiUrl);
  
    // Use fetch to send a GET request to the API endpoint
    fetch(apiUrl, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then(data => {
      alert('Response: ' + data);
    //   location.reload();
    })
    .catch(error => {
      alert('Error:' + error.message);
    });
}