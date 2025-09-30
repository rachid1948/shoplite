# ShopLite

Mini-plateforme de commandes (démo pédagogique) en **Maven multi-modules**.

## Modules
- **commons** : DTOs / erreurs / utils partagés
- **inventory-api** : service stock (produits + quantités)
- **order-api** : service commandes (créer, lister, consulter)
- **gateway** : façade HTTP (point d’entrée unique)

## Roadmap (apprentissage étape par étape)
1. Squelette Maven multi-modules ✅
2. Plugins qualité (tests, Jacoco, Checkstyle)
3. Dépendances Spring Boot (Web, Data)
4. Endpoints en mémoire
5. Persistence H2 → Postgres
6. CI/CD (GitHub Actions)
7. Docker & Docker Compose
