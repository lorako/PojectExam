
let reloadHeroesButton = document.getElementById('reloadHeroes');
let clearButton=document.getElementById("clear")

reloadHeroesButton.addEventListener('click', reloadHeroes);

clearButton.addEventListener('click', clearFun);

function reloadHeroes() {
  let heroContainer = document.getElementById('hero-container');
  heroContainer.innerHTML = '';


  fetch('http://localhost:8080/api/heroes')
    .then(response => response.json())
    .then(json =>{

    json.forEach(hero => {
      let heroRow = document.createElement('tr');

      let nameCol = document.createElement('td');
      let creatorCol = document.createElement('td');
      let createdCol = document.createElement('td');
      let descriptionCol = document.createElement('td');
      let priceCol = document.createElement('td');
      let powerCol = document.createElement('td');
      let likesCol = document.createElement('td');
      let imageCol = document.createElement('td');
      let actionCol = document.createElement('td');

      nameCol.textContent = hero.heroName;
      creatorCol.textContent = hero.creator;
      createdCol.textContent = hero.created;
      descriptionCol.textContent = hero.description;
      priceCol.textContent = hero.price;
      powerCol.textContent = hero.power;
      likesCol.textContent = hero.likes;

      let image = document.createElement('img');
      image.src = hero.imgUrl;
      imageCol.appendChild(image);


      heroRow.appendChild(nameCol);
      heroRow.appendChild(creatorCol);
      heroRow.appendChild(createdCol);
      heroRow.appendChild(descriptionCol);
      heroRow.appendChild(priceCol);
      heroRow.appendChild(powerCol);
      heroRow.appendChild(likesCol);
      heroRow.appendChild(imageCol);
      heroRow.appendChild(actionCol);

      heroContainer.appendChild(heroRow);
      });
    })
    .catch(error => console.error('Error:', error));
}

function clearFun() {
  let heroContainer = document.getElementById('hero-container');
  heroContainer.innerHTML = '';
}