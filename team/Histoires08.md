# Histoires GROUPE 08
Informations récapitulatives concernant les différentes histoires.

## Quelques précisions
Un point correspond à une heure de travail par binôme (approximatif).  Par itération il faut accomplir 28 points.

----------------------

## Pondération

| Priorité/3 | N° | Description | Risque/3 | Heures/? | Points |
| ------ | ------ | ------ | ------ | ------ | ------ |
| 1 | [1](#Histoire-A) | Histoire 1| 2 | 24.5 | 24 |
|   | 4 | Histoires 4.1, 4.2 et 4.3 | 1 | 32 | 51 |
| 2 | 2 | Histoire 2 | 1 | | 30 |
|   | 3 | Histoire 3 | 2 | | 15 |
|   | 6 | Histoire 6 | 2 | | 8 |
|   | 7 | Histoire 7 | 3 | | 10 |
|   | 10 | Histoire 10 | 2 | | 32 |
| 3 | 5 | Histoire 5 | 2 | | 10 |
|   | 8 | Histoire 8 | 1 | | 19 |
|   | 9 | Histoire 9 | 1 | | 17 |
|   | 11 | Histoire 11 | 1 | | 20 |
|   | 12 | Histoire 12 | 3 | | 9 |
|   | 13 | Histoire 13 | 1 | | 15 |
|   | 14 | Histoire 14 | 2 | | 7 |
|   | 15 | Histoire 15 | 2 | | 12 |

----------------------

## Histoire 1

### Titre : Créer un utilisateur, login et mot de passe

### Description
En démarrant le programme, un visiteur peut créer un nouveau compte. Pour ce faire, le visiteur doit tout d'abord accepter les conditions du service définies par l'administrateur du système.
Par la suite, le visiteur indique ses informations personnelles (par exemple, nom de famille, prénom, nom d'utilisateur, adresse e-mail et mot de passe souhaité). Le compte sera créé lors de la confirmation de validité des données saisies.
Après confirmation, le visiteur devient utilisateur qui peut se connecter au système et modifier les informations de son profil. Toute modification faite sur son profil exige la "confirmation de la validité des nouvelles données", procédure qui doit être déclenchée avant que les changements entrent en vigueur.
Un seul utilisateur peut être connecté à tout moment.

### Priorité Client : 1

### Risque Développeurs : 2

### Introduit dans l'itération : 1

### Etat : Complétée

### Points :
Afin de déterminer le nombre de points de cette histoire, le groupe l'a scindée en les tâches suivantes :

- Interface : 5 points
- Interface login : 7 points
- Connexion (vérification des données) : 3 points
- Sauvegarde des informations : 2 points
- Modification du profil : 2 points
- Team work : 2 points
- Documentation : 3 points

TOTAL : 24 points

### Notes :
Le client a répondu aux questions suivantes du groupe : Y a-t-il un format imposé pour la sauvegarde des informations ou pouvons-nous utiliser un fichier texte ? Un fichier texte est suffisant. Devons-nous rédiger nous-mêmes les conditions de service ? Oui, il faut rédiger des conditions de service mais assez simples et courtes.

----------------------

## Histoire 2

### Titre : Gestion des projets : sauvegarde, modification du nom du projet, affichage informations

### Description
Chaque utilisateur connecté au système peut créer un diagramme uniquement à l'intérieur d'un projet. Pour ce faire, l'utilisateur a deux possibilités :
- créer un nouveau projet en spécifiant son nom;
- utiliser un projet déjà existant.
Tous les projets créés ou partagés avec l'utilisateur sont visibles sur sa page de gestion deprojets. Cet écran affiche, pour chaque projet, son titre, le nom de son créateur et la datede dernière modification. Depuis cet écran, l’utilisateur peut sélectionner un ou plusieursprojets pour les copier, les supprimer, les sauvegarder dans un répertoire ou les partager avecun autre utilisateur. De plus, l’utilisateur peut sélectionner un projet pour l’ouvrir et entrerdans le mode d’édition ou pour changer son titre. Quand un projet est en mode édition, lesystème garde trace de toutes les modifications effectuées et les enregistre automatiquementà la fermeture du fichier même.

### Priorité Client : 2

### Risque Développeurs : 1

### Introduit dans l'itération : 

### Etat :

### Points :
Afin de déterminer le nombre de points de cette histoire, le groupe l'a scindée en les tâches suivantes :

- Création d'un projet : 5 points
- Sauvegarde à la fermeture de fichier : 2 points
- Sauvegarde manuelle : 1 points
- Interface pour lister les projets : 6 points
- Copier, supprimer, partager un projet : 6 points
- Renommer un projet : 2 points
- Sauvegarde automatique : 3 points
- Travail d'équipe : 1 point

TOTAL : 26 points

----------------------

## Histoire 3

### Titre : Importation et exportation de fichiers

### Description
L’utilisateur peut importer plusieurs fichiers à la fois ou un répertoire ou une archive com-pressée (.tar.gz) à partir de son ordinateur. Les fichiers déjà chargés par l’utilisateur via lerépertoire ne devraient pas être chargés une seconde fois.

### Priorité Client : 2

### Risque Développeurs : 2

### Introduit dans l'itération : 

### Etat :

### Points :
Afin de déterminer le nombre de points de cette histoire, le groupe l'a scindée en les tâches suivantes :

- Importer : 3 points
- Décompresser : 3 points
- Exporter : 3 points
- Documentation : 4 points
- Travail d'équipe : 2 points

TOTAL : 15 points

----------------------

## Histoire 4.1

### Titre : Création de diagrammes - Partie 1

### Description
Un cadre de texte sera généré, que l'utilisateur pourra ultérieurement utiliser pour décrire son diagramme en langage TikZ.

### Priorité Client : 1

### Risque Développeurs : 1

### Introduit dans l'itération : 1

### Etat : Complétée

### Points :
Inteface de code : 4 points

TOTAL : 4 points

----------------------

## Histoire 4.2

### Titre : Création de diagrammes - Partie 2

### Description
L’utilisateur a deux possibilités pour créer un diagramme :
- utiliser la méthode point-and-click; (cette possiblité ne sera pas entièrement impléméntée dans cette histoire)
- utiliser le langage TikZ.
Dans le premier cas, l’utilisateur doit uniquement pouvoir définir, pour cette histoire, les caractéristiques initiales des noeuds et des liens qu’il veut utiliser, grâce à un panneau de configuration où l’utilisateur peut choisir le type du noeud (cercle, rectangle, triangle, ...), le type du lien (arc, arête, ...) et leurs caractéristiques graphiques (couleur, épaisseur, étiquette, ...). 
Dans le deuxième cas, l’utilisateur peut utiliser le cadre de texte pour décrire son diagramme en langage TikZ. Quand l’utilisateur termine son code, le cadre contenant le canevas produira le dessin du diagramme automatiquement.

### Priorité Client : 1

### Risque Développeurs : 1

### Introduit dans l'itération : 2

### Etat : Complétée

### Points :
- Configuration du panneau (liens et noeuds) : 13 points
- Conversion du coe TikZ en diagramme : 15 points

TOTAL : 28 points

### Notes :
Lorsqu'un élément a été défini à partir du panneau de configuration, les caractéristiques de celui-ci (au format texte) seront inscrites dans un label "Elements ajoutés".

----------------------

## Histoire 4.3

### Titre : Création de diagrammes - Partie 3

### Description
L’utilisateur a deux possibilités pour créer un diagramme :
- utiliser la méthodepoint-and-click;
- utiliser le langage TikZ.
Dans le premier cas, quand un élément (noeud/lien) est défini à partir du panneau de configuration, l’utilisateur peut le sélectionner et le placer dans un cadre de type point-and-click contenant un canevas. Cette action a pour effet de déposer l’élément sur le canevas et en même temps la production du code TikZ correspondant qui est visible dans undeuxième cadre de texte associé.

### Priorité Client : 1

### Risque Développeurs : 1

### Introduit dans l'itération : 

### Etat : 

### Points :
- Point-and-click : 5 points
- Conversion du diagramme en code TikZ : 7 points
- Documentation : 4 points
- Travail d'équipe : 3 points

TOTAL : 19 points

----------------------


## Histoire 5

### Titre : Placement relatif

### Description
Le langage TikZ permet de décrire le placement des noeuds de manière relative. L’utilisateurpeux choisir d’utiliser cette description pour son diagramme, c.à.d. que, durant la créationd’un diagramme, l’utilisateur peut décrire le placement d’un noeud relativement à la positiond’un autre noeud existant, soit en utilisant la syntaxe du langage TkiZ dans le cadre texte,soit en utilisant la méthodepoint-and-clicksur le canevas.Dans les deux cas, le système doit être capable de produire le code TikZ avec le placementrelatif correspondant.

### Priorité Client : 3

### Risque Développeurs : 

### Introduit dans l'itération : 

### Etat : 

### Points :

TOTAL : 10 points

----------------------

## Histoire 6

### Titre : Aperçu

### Description
Le système offre une fonctionnalité d’aperçu dans laquelle il est possible de générer un fichier pdf/image du diagramme. Pour ce faire, le système effectue une compilation LATEX du code TkiZ que décrit le diagramme et génère l’image/le fichier pdf correspondant. L’utilisateur peut immédiatement visualiser le résultat.

### Priorité Client : 2

### Risque Développeurs : 

### Introduit dans l'itération : 

### Etat : 

### Points :
- Générer PDF et image : 4 points
- Documentation : 3 points
- Travail d'équipe : 1 point

TOTAL : 8 points

----------------------

## Histoire 7

### Titre : Drag & Drop (Glisser-déposer)

### Description
L’utilisateur peut construire un diagramme TikZ de façon modulaire, en utilisant des éléments graphiques du diagramme, prédéfinis dans le logiciel (e.g. noeuds ronds, noeuds carrés, flèches, axes, . . .).Pour ce faire, il faut d’abord afficher ces éléments prédéfinis dans une partie dédiée de l’in-terface graphique. Ensuite, l’utilisateur clique sur un élément graphique avec le bouton principal de la souris (le gauche en mode droitier, le droit en mode gaucher), et, en maintenant ce bouton enfoncé, il fera glisser l’objet jusqu’à sa destination sur le diagramme. Une fois atteint la cible, l’utilisateur relâchera le bouton et il verra apparaître l’objet désirédans le diagramme. Lorsque le glisser-déposer aura été complété, le code correspondant au diagramme devra être mis à jour dans la partie de l’interface graphique prévue à cet effet.

### Priorité Client : 2

### Risque Développeurs : 3 (si histoire 4 déjà complétée)

### Introduit dans l'itération : 

### Etat : 

### Points :

TOTAL : 10 points

----------------------

## Histoire 8

### Titre : Intégration avec des services cloud existants

### Description
Chaque utilisateur dispose d’une quantité d’espace disque définie par l’administrateur, afin de pouvoir gérer le stockage de ces projets. Cet espace peut être étendu grâce à l’intégration avec des services de stockage cloud (e.g.Dropbox, Google Drive, Github). L’utilisateur doit pouvoir exporter ses projets vers le service web désiré ainsi qu’importer des fichier stockés sur le service web dans le système. L’exportation/importation pourra être effectuée pour un ou plusieurs fichiers à la fois. Les ystème devra signaler à l’utilisateur si il est en train de télécharger un ficher qui existe déjàdans le système. L’intégration devra être effectuée en utilisant les API dédiées fournies par les services mêmes.

### Priorité Client : 3

### Risque Développeurs : 1

### Introduit dans l'itération : 

### Etat : 

### Points :
- Délimiter espace disponible pour l'utilisateur : 5 points
- Uploader : 5 points
- Cloud : 3 points
- Documentation : 6 points

TOTAL : 19 points

----------------------

## Histoire 9

### Titre : Versioning

### Description
L’utilisateur a accès à un système de version via l’application. Le système de gestion des versions permet à l’utilisateur de suivre l’évolution d’un projet TikZ en fournissant les fonctionnalités suivantes :
- branch le branchement d’un projet.
- commit valider les modifications apportées à une certaine branche avec un message décrivant les changements
- revert annuler une ou plusieurs validations précédentes, pour rétablir une version précédente.
- merge fusionner deux branches, les modifications de l’une sont portées sur l’autre.
- diff présente la différence (en termes de code TikZ) entre la version actuelle et un commit précédent spécifié.
Les commits seront liées à l’utilisateur qui a effectué le commit. L’utilisateur aura accès à ces fonctionnalités à travers d’un menu désigné dans l’application.

### Priorité Client : 3

### Risque Développeurs : 1

### Introduit dans l'itération : 

### Etat : 

### Points :
- Branch : 2 points
- Commit : 5 points
- Revert : 2 points
- Merge : 3 points
- Diff : 5 points

TOTAL : 17 points

----------------------

## Histoire 10

### Titre : Support pour librairies TikZ spécifiques

### Description
En plus de la fontionnalité traditionnelle de dessin d’un diagramme (c.-à-d. placement des noeuds et des arcs entre les noeuds), les utilisateurs du système peuvent accéder à des modes de construction du diagrammes dédiés, qui utiliseront certaines librairies TikZ spécifiques. Plus précisément :
- mindmap - Librairie pour le dessin d’une carte des idées. La carte des idées sera structurée comme un arbre où la racine est le concept principal de la carte et tous les autres concepts seront encodés comme des noeuds fils dans l’arborescence.
- trees - Librairie pour le dessin d’un arbre. L’arbre est décrit comme un ensemble des noeuds et les relations de parenté entre eux.
- matrix - Librairie pour l’arrangement des noeuds sur une grille (c.-à-d. placement des noeuds en lignes et colonnes, comme les éléments d’une matrice).

### Priorité Client : 2

### Risque Développeurs : 2

### Introduit dans l'itération : 

### Etat : 

### Points :
- Mindmap : 8 points
- Trees : 8 points
- Matrix : 8 points
- Documentation : 5 points
- Travail d'équipe : 3 points

TOTAL : 32 points

----------------------

## Histoire 11

### Titre : Coloration syntaxique code TikZ

### Description
Les utilisateurs peuvent profiter d’une fonctionnalité de coloration syntaxique du code LaTeX associé au diagramme sur lequel ils sont en train de travailler. Cette fonctionnalité doit colorer les mots clés du langage TikZ d’une manière différente du reste du code, afin de simplifier la gestion du code à l’utilisateur. En outre, quand l’utilisateur sélectionne un élément graphique dans la section dédiée de l’interface graphique, le code correspondant à cet élément doit être mis en évidence dans la section affichant le code LaTeX/TikZ.

### Priorité Client : 3

### Risque Développeurs : 1

### Introduit dans l'itération : 

### Etat : 

### Points :
- Coloration : 3 points
- Mise en évidence : 15 points
- Travail d'équipe : 2 points

TOTAL : 20 points

----------------------

## Histoire 12

### Titre : Section d'aide

### Description
Les utilisateurs peuvent accéder à une section d’aide dans le programme. Avec cet outil, les utilisateurs peuvent obtenir des informations plus détaillées et des explications sur comment utiliser les différentes fonctionnalités offertes par le programme. Éventuellement, un tutoriel démontrant l’utilisation de certaines fonctionnalités pourrait être démarré à partir de cette section.

### Priorité Client : 3

### Risque Développeurs : 3

### Introduit dans l'itération : 

### Etat : 

### Points :

TOTAL : 9 points

----------------------

## Histoire 13

### Titre : Sécurité des données

### Description
Le logiciel doit répondre à des besoins spécifiques concernant la sécurité informatique. Toutd’abord, les données sauvegardées ne doivent pas être accessibles par des personnes tierces (c’est-à-dire que les données doivent être sauvegardées de manière confidentielle). L’application offre la possibilité de protéger par mot de passe un fichier ou une exportation de projet. L’application ne pourra ouvrir ou importer le fichier ou le projet protégé que si le bon mot de passe est entré.

### Priorité Client : 3

### Risque Développeurs : 1

### Introduit dans l'itération : 

### Etat : 

### Points :

TOTAL : 15 points

----------------------

## Histoire 14

### Titre : Intégrité des données

### Description
Le logiciel doit garantir que les données stockées à l’intérieur de l’application ne puissent pas être altérées de façon fortuite, illicite ou malveillante. Le logiciel doit donc garantir l’absence de modifications non autorisées.

### Priorité Client : 3

### Risque Développeurs : 2 

### Introduit dans l'itération : 

### Etat : 

### Points :

TOTAL : 7 points

----------------------

## Histoire 15

### Titre : Copy-paste (copier coller)

### Description
L’utilisateur peut sélectionner une section du diagramme, la copier, et par la suite la coller à la position actuelle du curseur. L’action de copie et l’action de collage auront des raccourcis clavier respectifs.

### Priorité Client : 3

### Risque Développeurs : 2

### Introduit dans l'itération : 

### Etat : 

### Points :

TOTAL : 12 points

----------------------
