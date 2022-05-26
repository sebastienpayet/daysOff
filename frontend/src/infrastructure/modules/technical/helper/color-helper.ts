import {hashCode} from '@modules/technical/helper/string-helper';

export function stringToRGB(str: string): string {
  const i = hashCode(str);
  // eslint-disable-next-line no-bitwise
  const c = (i & 0x00FFFFFF)
    .toString(16)
    .toUpperCase();
  const colorCode = '00000'.substring(0, 6 - c.length) + c;
  return '#' + colorCode;
}

export function stringToHSL(str: string, saturation?: number, lightness?: number): string {
  const s = (saturation) ? Math.max(1, Math.min(100, saturation)) : 100;
  const l = (lightness) ? Math.max(1, Math.min(100, lightness)) : 30;
  const i = hashCode(str);
  const shortened = i % 360;
  return 'hsl(' + shortened + ',' + s + '%,' + l + '%)';
}
