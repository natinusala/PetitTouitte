# PetitTouitte
Projet de Web Cloud &amp; Datastore de M1 ALMA

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
7. Glissez-déposez le dossier `war` du dépôt vers votre projet dans la vue `Package Explorer`, et choisissez `Link to files and folders
    - Cela devrait lier le dossier `war` dans votre projet en l'ajoutant en bas du dossier `build` dans le `Package Explorer`
