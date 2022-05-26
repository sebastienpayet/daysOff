#language: fr

Fonctionnalité: : lister les demandes de congés

  Contexte:
    Etant donné que des equipes existent
      | nom          |
      | MANAGEMENT   |
      | LOCAL_POLICE |

    Etant donné que des utilisateurs existent
      | prenom | nom       | role          | services     | avecOuSansEnfantMineur | email              |
      | Elise  | Moualala  | ADMINISTRATOR | MANAGEMENT   | avec                   | elise@test.com     |
      | Arthur | Rololo    | USER          | LOCAL_POLICE | sans                   | arthur@test.com    |
      | Jose   | DeLaPampa | TEAM_MANAGER  | LOCAL_POLICE | sans                   | delapampa@test.com |

