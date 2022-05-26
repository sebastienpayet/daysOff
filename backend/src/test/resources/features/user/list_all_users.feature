#language: fr

Fonctionnalité: : lister les usagers de manière complète

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
      | prenom    | nom         | role          | services                          | avecOuSansEnfantMineur | email              |
      | Elise     | Moualala    | ADMINISTRATOR | MANAGEMENT                        | avec                   | elise@test.com     |
      | Arthur    | Rololo      | USER          | LOCAL_POLICE                      | sans                   | arthur@test.com    |
      | Jose      | DeLaPampa   | TEAM_MANAGER  | LOCAL_POLICE                      | sans                   | delapampa@test.com |
      | Robert    | Dupont      | USER          | ROADWORK                          | sans                   | add1@test.com      |
      | Sébastien | Vermelin    | USER          | LOCAL_POLICE                      | avec                   | add2@test.com      |
      | Fathia    | Chaipa      | USER          | SECRETARIAT                       | sans                   | add3@test.com      |
      | Max       | Lamenace    | USER          | TECHNICAL                         | avec                   | add4@test.com      |
      | José      | De la pampa | USER          | TECHNICAL, ROADWORK               | avec                   | add5@test.com      |
      | Louise    | Labrocante  | USER          | SCHOOL, PADDING_POOL, SECRETARIAT | sans                   | add6@test.com      |


  Scénario: en tant qu'administrateur, je peux lister tous les usagers de manière complète
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Quand je liste tous les usagers avec toutes leurs informations
    Alors j'obtiens tous les utilisateurs avec leurs informations complètes
    Et aucune erreur n'a été produite

  Scénario: en tant que manager, je ne peux pas lister tous les usagers de manière complète
    Etant donné que je suis connecté en tant que MANAGER
    Quand je liste tous les usagers avec toutes leurs informations
    Alors Une erreur signalant que je n'ai pas les droits est produite

  Scénario: en tant que manager, je peux lister de manière restreinte tous les usagers
    Etant donné que je suis connecté en tant que MANAGER
    Quand je liste tous les usagers avec des informations contrôlées
    Alors j'obtiens tous les utilisateurs des autres équipe avec leurs informations restreintes
    Et j'obtiens tous les utilisateurs de mes équipes avec leurs informations complètes
    Et aucune erreur n'a été produite

