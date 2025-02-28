# Lol-FaceMatch
## Dein Gesicht, Dein Champion
## Zli Moudl 335
## Link Github Pages (For App info): https://atm8048.github.io/Lol-FaceMatch/
### The Api, Dependency that are used:
1. Api for Lol Champions: https://ddragon.leagueoflegends.com/cdn/14.3.1/data/en_US/champion.json 
2. DialogFragment for Camera and Gallery: https://github.com/jrvansuita/PickImage
3. A type-safe HTTP client: https://github.com/square/retrofit
4. For Image Download and Show: https://github.com/square/picasso
5. For Face Detection: https://developers.google.com/ml-kit/vision/face-detection?hl=de
6. For Style Formating: https://github.com/JLLeitschuh/ktlint-gradle + https://plugins.jetbrains.com/plugin/15057-ktlint
The Plugin run automatically and the ktlint-gradle can be run with ./gradlew ktlintCheck --info
### Changes that are made after the Planing
1. CameraService and GalleryService are not implemented bcs of the PickImage DialogFragment
2. In MainActivity openGallery() and takePhoto() not implemented, the openPickImageDialog() is implemented
3. FaceFeatures Data Class is added, is not in the planning.
4. in ChampionFetcher is getChampionDetail() not implemented bcs is implemented the getRandomChampion()
5. Added ChampionApi, ChampionResponse, their are no in the planning.
6. The criterion: (Verwenden von Hintergrundoperationen (z. B. Services, Notifications, ...)) is not implemented
7. Champion Data Class has more properties that in the planning like tags.