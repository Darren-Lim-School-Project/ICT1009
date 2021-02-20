/**
 * @param {string} word
 * @returns {string}
 */
export const capitalize = (word) =>
    word.charAt(0).toUpperCase() + word.slice(1);

/**
 * @param {number} num
 * @param {number} places
 * @returns {number}
 */
export const roundTo = (num, places = 2) =>
    +(Math.round(num + "e+" + places) + "e-" + places);
