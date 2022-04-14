async function registerUser(user) {
  
    const request = await fetch('api/users', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(user)
    });
    alert("La cuenta fue creada con exito!");

  }