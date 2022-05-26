#language: fr

Fonctionnalité: : lister des équipes

  Contexte:
    Etant donné que des equipes existent
      | nom          |
      | MANAGEMENT   |
      | LOCAL_POLICE |
      | SERVICE1     |
      | SERVICE2     |
      | SERVICE3     |
      | SERVICE4     |
      | SERVICE5     |
      | SERVICE6     |

    Etant donné que des utilisateurs existent
      | prenom   | nom       | role          | services                       | avecOuSansEnfantMineur | email              |
      | Elise    | Moualala  | ADMINISTRATOR | MANAGEMENT                     | avec                   | elise@test.com     |
      | Arthur   | Rololo    | USER          | LOCAL_POLICE                   | sans                   | arthur@test.com    |
      | Jose     | DeLaPampa | TEAM_MANAGER  | LOCAL_POLICE,SERVICE1,SERVICE2 | sans                   | delapampa@test.com |
      | Manager1 | Manager1  | TEAM_MANAGER  | LOCAL_POLICE,SERVICE5,SERVICE6 | sans                   | manager@test.com   |
      | Manager2 | Manager2  | TEAM_MANAGER  | LOCAL_POLICE,SERVICE3,SERVICE4 | sans                   | manager@test.com   |
      | user1    | user1     | USER          | SERVICE1                       | sans                   | service1@test.com  |
      | user2    | user2     | USER          | SERVICE2,SERVICE1              | sans                   | service2@test.com  |
      | user3    | user3     | USER          | SERVICE3,SERVICE5              | sans                   | service3@test.com  |
      | user4    | user4     | USER          | SERVICE4,SERVICE3,SERVICE6     | sans                   | service4@test.com  |

  Scénario: en tant qu'administrateur, je peux lister toutes les équipes
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Quand je liste toutes les équipes
    Alors j'obtiens toutes les équipes
    Et aucune erreur n'a été produite

  Plan du scénario: en tant que manager ou utilisateur, je ne peux lister que mes équipes
    Etant donné que je suis connecté en tant que "<nom>" "<prenom>"
    Quand je liste toutes les équipes
    Alors je n'obtiens que les équipes ou "<nom>" "<prenom>" est affecté
    Exemples:
      | prenom   | nom       |
      | Jose     | DeLaPampa |
      | Manager1 | Manager1  |
      | Manager2 | Manager2  |
      | Arthur   | Rololo    |
      | user1    | user1     |
      | user2    | user2     |
      | user3    | user3     |
      | user4    | user4     |

