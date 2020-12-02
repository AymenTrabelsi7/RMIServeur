# Projet Chat RMI

NB : Dans ce document je n’expliquerai que la version du programme avec les callbacks (**branche version-callback**), cependant la version « normale » est disponible sur le master.

## Description Générale

L’application consiste en 2 projets séparés sur Eclipse : un Serveur et un Client.

Côté client, l’utilisateur est invité à entrer un nom d’utilisateur. S’il est déjà pris par un autre client déjà connecté, il lui propose d’en choisir un autre. Une fois un pseudo choisi, le client envoie son callback au serveur, et il peut commencer à envoyer des messages. Lorsqu’il envoie un message, les autres clients le reçoivent, avec le nom de l’expéditeur (ils ne reçoivent pas leur propre message).  Les clients sont avertis lorsqu’un utilisateur a rejoint ou quitté le chat. Pour quitter ils doivent entrer /quit.

Côté serveur, lorsque le programme reçoit une demande d’entrée d’un nouvel utilisateur, le serveur vérifie si le nom d’utilisateur existe déjà, ensuite il lui assigne un numéo identifiant aléatoire, puis il stocke le callback et le username dans deux HashMap différentes, mais avec la même clé (l’identifiant). Cela permettra de retrouver l’username avec le callback et inversement.

## Partie Serveur

Le projet Serveur est constitué de 3 programmes : Serveur, MainServeur et ServeurIntf (+ les programmes du Client qu’il doit avoir). 

MainServeur est le main du projet, c’est là que le registre RMI sera déclaré. Serveur est la classe qui implémente ServeurIntf, et qui contient les fonctions qui gèrent le transfert des messages ainsi que la mise à jour des utilisateurs.

### Méthodes de Serveur :

  #### newUser() :
  
  Permet d’ajouter un utilisateur dans le chat.
  
  Demande l’username et le callback à appeler en cas de nouveau message. Retourne false si l’username est déjà pris, true si tout se passe bien. 
  
  La méthode boucle ensuite à travers les callbacks afin de publier le message qui signale aux utilisateurs qu’un nouveau client s’est connecté. Si jamais un utilisateur se déconnecte à ce moment, afin de ne pas déclencher un RemoteException, la méthode stocke les identifiants pour lesquels ça n’a pas marché, et les supprime à la fin de la boucle forEach.
  
  #### ecrireMessage():
  
  Permet de rediriger le message envoyé vers les autres clients.
  
  Le client expéditeur spécifie son nom et le message, et le serveur boucle à travers tous les callbacks et appelle les méthodes.
  
  Idem ici il y a le même système qui permet de gérer la déconnexion spontanée des utilisateurs.
  
  #### userLeft() :
  
  Est appelé par le client quand il veut quitter le chat volontairement ("/quit"). 
  
  Prend en paramètre le nom de l'utilisateur, et boucle dans les callbacks et la liste des username pour le supprimer.
  
  #### userNumber() :
  
  Retourne le nombre d'utilisateurs connectés.


## Partie Client

Le projet Client est constitué de 4 programmes : Client, MainClient, ClientCallback et CBClient + ServeurIntf qu'il doit connaître. 

MainClient est le programme principal : il instancie la classe client, demande un username à l'utilisateur et envoie le callback au serveur. Il envoie ensuite les messages entrés par l'utilisateur.

La classe Client contient 3 attributs uniquement (et leur getters/setters) : l'username, l'interface ServeurIntf qui permet d'éxécuter les méthodes du Serveur en RMI, et un objet de la classe ClientCallback, qui implémente l'interface CBClient et qui contient les différents callbacks.

### Méthodes de ClientCallback :


  #### notifyMe() :
  
  Callback qui affiche un nouveau message dans la console du client. Prend en paramètre le message et l'username de l'expéditeur.
  
  #### messageNew() :
  
  Callback qui notifie les utilisateurs qu'un nouveau client s'est connecté. Prend en paramètre le nom d'utilisateur.
  
  #### messageLeft() :
  
  Callback qui notifie les utilisateurs qu'un client a quitté le chat. Prend en paramètre le nom d'utilisateur.


  Est appelé par le client quand il veut quitter le chat volontairement ("/quit"). 
  
  Prend en paramètre le nom de l'utilisateur, et boucle dans les callbacks et la liste des username pour le supprimer.
  
  d. userNumber() :
  
  Retourne le nombre d'utilisateurs connectés.
