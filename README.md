# NNPIA Semestrální práce

## Popis aplikace

Aplikace umožnňuje uživatelům hodnotit a komentovat filmy.

## Databázový model
![model](docs/model.png)

## API

* User controller
  * [PUT]     /user/changeUserPassword/{userId} - Change user password
  * [POST]    /user/signup - Sign up a new user
  * [POST]    /user/login - Login user
  * [POST]    /user/addUser - Add a new user
  * [POST]    /user/addFilm - Add a new film
  * [GET]     /user - Get all users
  * [GET]     /user/{userId} - Get user info by ID
  * [DELETE]  /user/{userId} - Remove user by ID
  * [GET]     /user/getAllRoles - Get all roles

* Person-controller
  * [GET]     /person/{id} - Get person by ID
  * [PUT]     /person/{id} - Update person by ID
  * [GET]     /person - Get all persons
  * [POST]    /person - Add a new person
  * [DELETE]  /person/{personId} - Remove person by ID

* Film-controller
  * [GET]     /film/{id} - Get film by ID
  * [PUT]     /film/{id} - Update film by ID
  * [DELETE]  /film/{id} - Remove film by ID
  * [GET]     /film - Get all films
  * [POST]    /film - Add a new film
  * [POST]    /film/addPerson - Add a person to a film
