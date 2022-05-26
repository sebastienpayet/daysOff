export class Token {
  id: string;
  value: string;
  lifetime: number;
  inactivityTime: number;
  userId: string;
  userRole: string;
  nonce: Date;

  static fromEncryptedToken(encrypted: string): Token {
    return null;
  }
}
