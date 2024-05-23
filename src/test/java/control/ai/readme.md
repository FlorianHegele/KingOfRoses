# Tests AI Camarade

## Concept

L'IA **Camarade** joue de la façon suivante :
- Pose un pion si elle le peut sans empiéter sur un pion adverse
    - lorsqu'elle peut poser un pion elle choisi celui qui lui donnerait le plus de points
- Pioche une carte
- En dernier recours, pose un pion sur un pion adverse

## Tests

### Situations pose de pions sans empiéter et max de points

- **Carte jouables obligatoires**
- Plateau vide
- Choix entre pions adverse ou case vide
- Choix entre différents coups qui n'empiètent pas sur un pion adverse
- Choix entre différents coups dont certains qui empiètent sur l'adversaire
- Choix entre pioche et jouer une carte qui n'empiètent pas sur un pion adverse
- Choix entre pioche et attaquer l'adversaire
