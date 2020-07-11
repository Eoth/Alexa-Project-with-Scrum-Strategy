# Alexa-Project-with-Scrum-Strategy
goose game with alexa participation

![alexa](https://img.shields.io/static/v1?label=alexa&message=developer&color=blue)
![spring](https://img.shields.io/static/v1?label=spring&message=boot&color=success)
![docker](https://img.shields.io/static/v1?label=docker&message=image&color=blue)
![aws](https://img.shields.io/static/v1?label=Aws&message=RDS_Lambda&color=orange)
![google](https://img.shields.io/static/v1?label=google&message=engine&color=gray)

## Les branches du git

##### master : le readme pour le guide 
##### dev-goose-game : le code de notre application du jeu de l'oie
##### dev-api : le code de l'api de notre jeu

## Le jeu de l'oie
##### Pincipe du jeu
* Deux joueurs ou plus lancent chacun à leur tour deux dés, et se déplacent sur le plateau en fonction du résultat du dé
* il existe des cases spéciales qui font avancer le joueur de x cases ou le fait reculer de x cases
* le premier joueur qui arrive sur la case finale, remporte la partie.

##### Notre jeu
* le jeu contient 50 cases
* les cases[5] [15] [25] [35] font avancer de 4 cases
* les cases [7] [17] [27] [37] [47] font reculer de 4 cases
* la case [48] fait revenir le joueur à la case de départ

## Guide d'insatallation Alexa
##### 1 - Télécharger l'application alexa 
* Se connecter avec le compte amazon developer
  __login__ : tibepakyendou.nakote@etu.uphf.fr
  __password__ : ********
* Avec ce compte,on peut se connecter également sur https://developer.amazon.com pour voir le skill développé pour Alexa 
##### 2 - Installation de Amazon Echo
* Cliquer sur l'icône menu en haut en à gauche
* brancher amazon, appuyez sur le bouton (__.__) pour être en mode configuration
* selectionner ajouter un appareil
* suivre l'instruction jusqu'à l'installation
##### 3 - Lancer la skill
* Brancher Amazon Echo 
* Pour lancer la skill jeu de l'oie __Dites : Alexa commencer jeu__
## Développement de la skill alexa
##### 1 - Amazon developper console
* Création de la skill
* les mots d'invocation de la skil et de ses différentes phrases d'interactions avec alexa
##### 2 - Amazon Web Service lambda
* créer un AWS lambda
* Dans la skill sur Amazon developer console se trouve un entrypoint dans lequel on lie le AWS lambda grace à son ID
* Dévéloper le code et deposer sur Lambda sous forme de zip ou jar

## Gestion de l'Api
* l'Api est disponible sur le lien http://35.205.140.234:8080/swagger-ui.html (*__le serveur n'est plus disponible__*)
* Sur cette api deux contrôleurs sont disponibles 
  > **Game_party_Controller qui permet :**
     * d'afficher la liste des joueurs
     * créer une liste
     * ajouter des joueurs à la partie
     * supprimer  joueur de la partie
     * supprimer partie
  > <espace>
  
  > **Player_controller :**
     * créer un joueur
     * mettre à jour le joueur (*__Permet de modifier la position du joueur en cours de partie__*) 
     * suppprimer le joueur 
     
     


