# KingOfRoses
King of Roses

# **1°/ Matériel et mise en place**

- Le plateau est constitué d'une grille 9x9, avec placé au centre un pion nommé "roi" dans la suite.
- Il y a 52 pions bifaces, rouge d'un côté et bleu de l'autre, que l'on pose à côté du plateau. On peut éventuellement préparer 26 jetons côté rouge visible et 26 jetons côté bleu, car il est très peu probable qu'un joueur en pose plus de 26 au cours d'une partie.
- Chaque joueur reçoit également 4 cartes "Héros" qu'il va pouvoir jouer lors de ses tours de jeu. Une fois jouées, elles ne sont **jamais** remplacées et sont mises de côté.
- Il y a également 24 cartes "Déplacement" qui constituent la pioche. Une carte Déplacement indique en bas un nombre en chiffre romain allant de 1 à 3, ainsi qu'une direction de déplacement pour le roi. Vu qu'il y a 8 directions possibles, cela implique qu'une combinaison nombre+direction n'existe qu'en un seul exemplaire.
- Au début de la partie les 24 cartes sont mélangées et chaque joueur en reçoit 5, face visible, qu'il doit placer avec **le chiffre vers lui**. La direction indiquée est donc celle vue par le joueur. Les joueurs pourront éventuellement en repiocher au cours de la partie.
- L'état du plateau en début de partie est illustré par la figure 1 ci-dessous

![img](https://github.com/FlorianHegele/KingOfRoses/assets/144370530/6a7be335-0137-42eb-94cd-f3583b394e58)

# **2°/ Régles**

## 2.1°/ Déroulement du jeu

- Le premier joueur est déterminé par tirage au sort ou bien par accord.
- Chacun son tour, chaque joueur choisit une action parmi les 3 possibles, expliquée ci-dessous
- Cette alternance se poursuit jusqu'à la fin de partie, dont les conditions sont données en section 2.3

## 2.2°/ Actions de jeu

- Il existe 3 actions possibles :
    - piocher une carte déplacement,
    - jouer une carte déplacement seule,
    - jouer une carte déplacement plus une carte héros.

### 2.2.1°/ Piocher une carte déplacement

- Cette action n'est possible que si le joueur a moins de 5 cartes déplacement devant lui.
- Il pioche la carte du dessus de la pioche et la place face visible, orientée de façon à avoir le numéro devant lui.
- Si la pioche est vide au moment de piocher, il suffit de mélanger la défausse pour refaire une pioche

### 2.2.2°/ Jouer une carte déplacement seule.

- Jouer une carte déplacement seule consiste à déplacer le roi dans la direction indiquée, du nombre de cases indiqué.
- On ne peut pas jouer une carte :
    - dans une direction autre que celle indiquée par la carte,
    - avec moins de cases que le nombre indiqué par la carte,
    - si le déplacement fait sortir le roi du plateau,
    - si le déplacement amène le roi sur un pion (du joueur courant ou de l'adversaire).
- A noter que le roi peut "sauter" par dessus des pions au cours de son déplacement.
- Si la carte choisie par le joueur peut être jouée, alors :
    - il la pose dans une défausse à côté du plateau,
    - il pose un pion avec sa couleur visible sur la case d'arrivée,
    - il pose le roi sur le pion qu'il vient de poser dans la case d'arrivée.

### 2.2.3°/ Jouer une carte déplacement plus une carte héros

- Jouer un binôme de cartes déplacement+héros consiste à déplacer le roi dans la direction indiquée, du nombre de cases indiqué, pour atterrir sur une case occupée par un pion de la couleur adverse.
- On ne peut pas jouer un tel binôme :
    - dans une direction autre que celle indiquée par la carte déplacement,
    - avec moins de cases que le nombre indiqué par la carte déplacement,
    - si le déplacement n'amène pas le roi sur un pion de l'adversaire.
- A noter que le roi peut "sauter" par dessus des pions au cours de son déplacement.
- Si le binôme de cartes choisi par le joueur peut être joué, alors :
    - il pose la carte déplacement dans une défausse à côté du plateau, et la carte héros dans une autre défausse. Il ne pourra jamais récupérer cette carte héros.
    - il retourne le pion de la case d'arrivé afin de changer sa couleur visible.
    - il pose le roi sur le pion qu'il vient de retourner dans la case d'arrivée.

## 2.3°/ Fin de partie

- La partie s'arrête dans 2 cas :
    - dès qu'un joueur a 5 cartes déplacement posées devant lui et il ne peut en jouer aucune.
    - dès qu'un joueur pose le dernier pion disponible.
- Pour déterminer le gagnant, il faut déterminer :
    - le nombre de zones "d'adjacence",
    - compter pour chaque zone le nombre de pions dans cette zone, et mettre au carré ce nombre pour obtenir la valeur de la zone
    - faire la somme des valeurs des zones.
- Une zone d'adjacence est constituée par un ensemble de cases dont les jetons sont de la même couleur ET qui se touchent 2 à 2 par les côtés (pas par les coins).
- Le joueur qui a la somme la plus élevée gagne la partie.
- En cas d'égalité des sommes, le gagnant est celui qui a le plus de pions de sa couleur sur la plateau,
- Si ce nombre de pions est égale, la partie est nulle et il n'y a pas de gagnants.

Exemple : On suppose que la plateau final se trouve dans l'état de la figure 2, ci dessous
![img_1](https://github.com/FlorianHegele/KingOfRoses/assets/144370530/89409ab6-a1e0-45ee-9509-a1e4d5e21339)

- Le joueur bleu marque 69 points, grâce à :
    - une zone de 8, soit 64 points,
    - une zone de 2, soit 4 points,
    - une zone de 1, soit 1 point.
- Le joueur rouge marque 34 points, grâce à :
    - une zone de 5, soit 25 points,
    - deux zones de 2, soit 8 points,
    - une zone de 1, soit 1 point.
- Le gagnant est donc le joueur bleu.
