# PetitTouitte
Projet de Web Cloud &amp; Datastore de M1 ALMA

Lien vers le front-end : http://plnkr.co/edit/VOrO26yAVSJozs5wG2Li?p=preview

Lien vers l'explorateur de l'API : https://apis-explorer.appspot.com/apis-explorer/?base=https://petittouitte2.appspot.com/_ah/api#p/

## Compilation

Pour compiler et exécuter le projet sur votre propre projet App Engine :


1. Installez Eclipse avec le plugin Google App Engine
2. Paramétrez le plugin en vous connectant à votre compte
3. Créez un nouveau projet `Google App Engine`, donnez lui les paramètres suivants :
    - Package `petittouitte`
4. Supprimez les dossiers `src` et `war`
    - N'oubliez pas de supprimer les dossiers dans le workspace et d'actualiser le projet
5. Dans un dossier à part, clonez ce dépôt
6. Dans les propriétés du projet, section `Java Build Path`, onglet `Source`, bouton `Link Source` :
    - `Linked folder location` : le chemin vers le dossier `src` de ce dépôt
    - `Folder name` : `src`

## Spécification de l'API

https://docs.google.com/document/d/1_BN5aj-6OwSGUKuFW0WvgPyNOQaWXK6lMukGHe_klMI/edit?usp=sharing
