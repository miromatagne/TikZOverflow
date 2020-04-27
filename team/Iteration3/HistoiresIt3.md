# Histoires pour l'itération 3
Pour l'itération 3, le groupe 08 devra réaliser l'histoire 4.3 et une partie de l'histoire 7, soit l'histoire 7.1. Ces deux histoires correspond à un total de 28 points pour tout le groupe.
Plus tard dans l'itération, le groupe 08 a jugé impératif d'effectuer un grand refactoring sur le code afin de corriger les remarques données par l'assistant lors du Code Review.
C'est pourquoi l'histoire 16 a été ajoutée aux tâches à compléter lors de cette itération.

## Histoire 4.3

### Titre : Création de diagrammes - Partie 3

### Description
L’utilisateur a deux possibilités pour créer un diagramme :
- utiliser la méthode point-and-click;
- utiliser le langage TikZ.

Dans le premier cas, quand un élément (noeud/lien) est défini à partir du panneau de configuration, l’utilisateur peut le sélectionner et le placer dans un cadre de type point-and-click contenant un canevas.
Cette action a pour effet de déposer l’élément sur le canevas et en même temps la production du code TikZ correspondant qui est visible dans undeuxième cadre de texte associé.


### Priorité Client : 1

### Risque Développeurs : 1

### Introduit dans l'itération : 3

### Etat : Complétée

### Points :

- Point-and-click : 5 points
- Conversion du diagramme en code TikZ : 7 points
- Documentation : 4 points
- Travail d'équipe : 3 points

TOTAL : 19 points

Cette histoire a été réalisée en 6 points. Ceci est dû notamment au fait que le client ait décidé de retirer le point-and-click, comme précisé dans les notes ci-dessous. D'autre part, lors de l'itération 2, quelques points supplémentaires avaient été nécessaires, principalement au niveau de la documentation, afin d'impléménter ce qui était alors demandé. Ceci a également accéléré la réalisation de cette histoire.

### Notes :

Les coordonnées des éléments graphiques seront directement décidées depuis le panneau de configuration.

----------------------

## Histoire 7.1

### Titre : Drag & Drop (Glisser-déposer) sans le mode gaucher

### Description
L’utilisateur peut construire un diagramme TikZ de façon modulaire, en utilisant des éléments graphiques du diagramme, prédéfinis dans le logiciel (e.g. noeuds ronds, noeuds carrés, flèches, axes, . . .).
Pour ce faire, il faut d’abord afficher ces éléments prédéfinis dans une partie dédiée de l’interface graphique. Ensuite, l’utilisateur clique sur un élément graphique avec le bouton principal de la souris 
(le gauche en mode droitier), et, en maintenant ce bouton enfoncé, il fera glisser l’objet jusqu’à sa destination sur le diagramme. Une fois atteint la cible, 
l’utilisateur relâchera le bouton et il verra apparaître l’objet désiré dans le diagramme. Lorsque le glisser-déposer aura été complété, le code correspondant au diagramme devra être mis à jour dans la partie
de l’interface graphique prévue à cet effet.

### Priorité Client : 2

### Risque Développeurs : 3 (si histoire 4 déjà complétée)

### Introduit dans l'itération : 3

### Etat : Complétée

### Points :

- Créer panneau de formes prédéfinies : 3 points
- Dépôt sur le canevas : 3 points
- Affichage des formes pendant le "drag" : 3 points

TOTAL : 9 points

Cette histoire a en fait été réalisée en 26 points. 
Cette importante augmentation par rapport aux prévisions est principalement dûe au large refactoring qui a été fait sur une grande partie 
du code (ceci est détaillé dans le document "DebriefingIt3").

### Notes :

Le mode gaucher n'est pas à réaliser pour cette itération, c'est pourquoi l'histoire 7 a été scindée en les histoires 7.1 et 7.2.

----------------------

