
let reloadUsersButton = document.getElementById('reloadUsers');
let clearButton = document.getElementById('clear');
let countrySearchButton = document.getElementById('searchButtonCountry');

reloadUsersButton.addEventListener('click', loadUsers);
clearButton.addEventListener('click', clearFun);
countrySearchButton.addEventListener('click', loadByCountry);

function loadUsers() {
    let userContainer = document.getElementById('user-container');
    userContainer.innerHTML = '';

    fetch('http://localhost:8080/api/users')
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch user data');
            }
            return response.json();
        })
        .then(users => {
            users.forEach(user => {
                let userRow = createUserRow(user);
                userContainer.appendChild(userRow);
            });

            loadByCountry(users);
        })
        .catch(error => {
            console.error('Error:', error.message);
        });
}

function createUserRow(user) {
    let userRow = document.createElement('tr');

    let nameCol = document.createElement('td');
    let emailCol = document.createElement('td');
    let ageCol = document.createElement('td');
    let countryCol = document.createElement('td');

    nameCol.textContent = user.username;
    emailCol.textContent = user.email;
    ageCol.textContent = user.age;
    countryCol.textContent = user.country;

    userRow.appendChild(nameCol);
    userRow.appendChild(emailCol);
    userRow.appendChild(ageCol);
    userRow.appendChild(countryCol);

    return userRow;
}


function clearFun() {
    let userContainer = document.getElementById('user-container');
    userContainer.innerHTML = '';

}

