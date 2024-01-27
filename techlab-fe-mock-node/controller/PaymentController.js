function submitForm() {
  const form = document.getElementById('paymentForm');
  const formData = new FormData(form);
  const paymentType = formData.get('type');
  const intent = formData.get('intent');
  

  let apiUrl = '';
  if (paymentType === 'Paypal') {
    apiUrl = 'http://localhost:3000/api/paypal/create';
  } else if (paymentType === 'Visa') {
    apiUrl = 'http://localhost:3000/api/visa/create';
  }

  console.log(Object.fromEntries(formData.entries()));
  console.log(apiUrl);

  fetch(apiUrl, {
    method: 'POST',
    body: JSON.stringify(Object.fromEntries(formData.entries())),
    headers: {
      'Content-Type': 'application/json'
    }
  })
  .then(response => response.json())
  .then(data => console.log(data))
  .catch(error => console.error(error));
}
