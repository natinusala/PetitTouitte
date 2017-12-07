# PetitTouitte
Projet de Web Cloud &amp; Datastore de M1 ALMA

## Compilation

Pour compiler et exécuter le projet sur votre propre projet App Engine :

1. Installez et paramétrez le Google Cloud SDK sur votre machine : `https://cloud.google.com/sdk`
2. Installez le component `app-engine-java` : `gcloud components install app-engine-java`
3. Installez Eclipse avec le plugin Google Cloud : `https://cloud.google.com/eclipse/docs/`
4. Paramétrez le plugin en vous connectant à votre compte, puis en lui donnant le chemin vers le SDK sur votre machine
5. Créez un nouveau projet `Google App Engine Standard Java Project`, donnez lui les paramètres suivants :
    - Java 8
    - Package `petittouitte`
    - Cochez `Google Cloud Endpoints` dans `Libraries to add to build path`
6. Supprimez le dossier `src`
    - N'oubliez pas de supprimer le dossier dans le workspace et d'actualiser le projet
7. Dans un dossier à part, clonez ce dépôt
8. Dans les propriétés du projet, section `Java Build Path`, onglet `Source`, bouton `Link Source` :
    - `Linked folder location` : le chemin vers le dossier `src/main/java` de ce dépôt
    - `Folder name` : `java`
9. Glissez-déposez le dossier `src` du dépôt vers votre projet dans la vue `Package Explorer`, et choisissez `Link to files and folders
    - Cela devrait lier le dossier `src` dans votre projet en l'ajoutant en bas du dossier `build` dans le `Package Explorer`
