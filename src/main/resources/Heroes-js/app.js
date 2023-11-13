

let reloadHeroesButton = document.getElementById('reloadHeroes');
reloadHeroesButton.addEventListener('click', reloadHeroes)



function reloadHeroes() {

  let heroContainer = document.getElementById('hero-container');
  heroContainer.innerHTML = ''

  fetch('http://localhost:8080/api/heroes')
    .then(response => response.json())
    .then(json => json.forEach(hero => {

      let heroRow = document.createElement('tr')

      let nameCol = document.createElement('td')
      let creatorCol	= document.createElement('td')
      let descriptionCol	= document.createElement('td')
      let priceCol= document.createElement('td')
      let createdCol= document.createElement('td')
      let likesCol= document.createElement('td')
      let powerCol= document.createElement('td')
      let imageCol= document.createElement('img')
      let actionCol=document.createElement('td')

      nameCol.textContent = hero.heroName
      creatorCol.textContent = hero.creator
      createdCol.textContent = hero.created
      descriptionCol.textContent = hero.description
      priceCol.textContent=hero.price
      powerCol.textContent=hero.power
      likesCol.textContent=hero.likes
      imageCol.src=hero.imgUrl


      let deleteBtn = document.createElement('button')
      deleteBtn.innerHTML = 'DELETE'
      deleteBtn.dataset.id = hero.id
      deleteBtn.addEventListener('click', deleteBtn)

      actionCol.appendChild(deleteBtn)

      heroRow.appendChild(nameCol)
      heroRow.appendChild(creatorCol)
      heroRow.appendChild(createdCol)
       heroRow.appendChild(priceCol)
      heroRow.appendChild(descriptionCol)
      heroRow.appendChild(likesCol)
      heroRow.appendChild(likesCol)
      heroRow.appendChild(powerCol)
      heroRow.appendChild(imageCol)
      heroRow.appendChild(actionCol)

      heroContainer.appendChild(heroRow)
    }))

}

function deleteBtnClicked(event) {

  let heroId = event.target.dataset.id;
  let requestOptions = {
    method: 'DELETE'
  }

  fetch(`http://localhost:8080/api/heroes/${heroId}`, requestOptions)
    .then(_ => reloadHeroes())
    .catch(error => console.log('error', error))
}