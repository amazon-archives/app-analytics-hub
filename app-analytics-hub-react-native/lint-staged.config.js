/* globals module */
// Since this module is set to use es6 thus module is giving error. Thus
// defining module as global before hand
module.exports = {
  linters: {
    '*.{js,json,css,md,ts,tsx}': [
      'npm run lint:files',
      // Assuming prettier doesn't add any linting issues
      'prettier --write',
      // Check if errors were added because of prettier
      'npm run lint:files',
      'git add'
    ]
  }
};
