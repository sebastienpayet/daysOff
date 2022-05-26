#language: fr

Fonctionnalité: : modifier un usager

  Contexte:
    Etant donné que des equipes existent
      | nom          |
      | ROADWORK     |
      | LOCAL_POLICE |
      | SECRETARIAT  |
      | TECHNICAL    |
      | SCHOOL       |
      | PADDING_POOL |
      | MANAGEMENT   |

    Etant donné que des utilisateurs existent
      | prenom | nom       | role          | services     | avecOuSansEnfantMineur | email              |
      | Elise  | Malala    | ADMINISTRATOR | MANAGEMENT   | avec                   | elise@test.com     |
      | Arthur | Rololo    | USER          | LOCAL_POLICE | sans                   | arthur@test.com    |
      | Jose   | DeLaPampa | TEAM_MANAGER  | LOCAL_POLICE | sans                   | delapampa@test.com |

  Plan du scénario: en tant qu'administrateur, je peux modifier de nouveaux usagers
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Et que je crée un USAGER "<nom>" "<prenom>" dans le service "<services>", "<avecOuSansEnfantMineur>" enfant mineur "<email>"
    Quand je modifie un USAGER "<nom>" "<prenom>" avec les informations "<nouveau_nom>" "<nouveau_prenom>" dans le service "<nouveau_services>", "<nouveau_avecOuSansEnfantMineur>" enfant mineur "<nouvel_email>"
    Alors l'usager "<nouveau_nom>" "<nouveau_prenom>" dans le service "<nouveau_services>", "<nouveau_avecOuSansEnfantMineur>"  enfant mineur "<nouvel_email>", existe dans l'application
    Et aucune erreur n'a été produite
    Exemples:
      | prenom    | nom             | services                          | avecOuSansEnfantMineur | email         | nouveau_prenom | nouveau_nom | nouveau_services       | nouveau_avecOuSansEnfantMineur | nouvel_email  |
      | Robert    | Dupont          | ROADWORK                          | sans                   | add1@test.com | Robert2        | Dupont2     | TECHNICAL              | avec                           | add2@test.com |
      | Sébastien | Gourmellin      | LOCAL_POLICE                      | avec                   | add1@test.com | Robert2        | Dupont2     | TECHNICAL,ROADWORK     | avec                           | add2@test.com |
      | Fathia    | Jesaispassonnom | SECRETARIAT                       | sans                   | add1@test.com | Robert2        | Dupont2     | TECHNICAL,SECRETARIAT  | avec                           | add2@test.com |
      | Max       | Lamenace        | TECHNICAL                         | avec                   | add1@test.com | Robert2        | Dupont2     | TECHNICAL,PADDING_POOL | avec                           | add2@test.com |
      | José      | De la pampa     | TECHNICAL, ROADWORK               | avec                   | add1@test.com | Robert2        | Dupont2     | TECHNICAL ,SCHOOL      | avec                           | add2@test.com |
      | Louise    | Labrocante      | SCHOOL, PADDING_POOL, SECRETARIAT | sans                   | add1@test.com | Robert2        | Dupont2     | SCHOOL                 | avec                           | add2@test.com |

  Plan du scénario: en tant qu'administrateur, je ne pas attribuer un email deja pris en modifiant un utilisateur
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Quand je modifie un USAGER "<nom>" "<prenom>" avec les informations "<nouveau_nom>" "<nouveau_prenom>" dans le service "<nouveau_services>", "<nouveau_avecOuSansEnfantMineur>" enfant mineur "<nouvel_email>"
    Alors Une erreur signalant que cet email est déjà pris est produite
    Exemples:
      | prenom | nom       | nouveau_prenom | nouveau_nom | nouveau_services | nouveau_avecOuSansEnfantMineur | nouvel_email   |
      | Jose   | DeLaPampa | Robert2        | Dupont2     | TECHNICAL        | avec                           | elise@test.com |


  Plan du scénario: en tant que manager, je ne peux pas modifier des usagers
    Etant donné que je suis connecté en tant que MANAGER
    Quand je modifie un USAGER "<nom>" "<prenom>" avec les informations "<nouveau_nom>" "<nouveau_prenom>" dans le service "<nouveau_services>", "<nouveau_avecOuSansEnfantMineur>" enfant mineur "<nouvel_email>"
    Alors Une erreur signalant que je n'ai pas les droits est produite
    Exemples:
      | prenom | nom       | nouveau_prenom | nouveau_nom | nouveau_services | nouveau_avecOuSansEnfantMineur | nouvel_email  |
      | Jose   | DeLaPampa | Robert2        | Dupont2     | TECHNICAL        | avec                           | add2@test.com |

  Plan du scénario: en tant qu'utilisateur, je ne peux pas modifier des usagers
    Etant donné que je suis connecté en tant qu'USAGER
    Quand je modifie un USAGER "<nom>" "<prenom>" avec les informations "<nouveau_nom>" "<nouveau_prenom>" dans le service "<nouveau_services>", "<nouveau_avecOuSansEnfantMineur>" enfant mineur "<nouvel_email>"
    Alors Une erreur signalant que je n'ai pas les droits est produite
    Exemples:
      | prenom | nom       | nouveau_prenom | nouveau_nom | nouveau_services | nouveau_avecOuSansEnfantMineur | nouvel_email  |
      | Jose   | DeLaPampa | Robert2        | Dupont2     | TECHNICAL        | avec                           | add2@test.com |
