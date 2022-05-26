import {Injectable} from '@angular/core';
import CryptoJS from 'crypto-js';

@Injectable({
  providedIn: 'root'
})
export class CryptService {

  decrypt(encryptedData: string, key: string): string {
    const {mode, enc: {Base64, Utf8}, AES} = CryptoJS;

    const keyForCryptoJS = Base64.parse(key);
    const decodeBase64 = Base64.parse(encryptedData);

    const decryptedData = AES.decrypt(
      {
        ciphertext: decodeBase64
      },
      keyForCryptoJS,
      {
        mode: mode.ECB /* Override the defaults */
      }
    );

    const decryptedText = decryptedData.toString(Utf8);
    return String(decryptedText);
  }
}
