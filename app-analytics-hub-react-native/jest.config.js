module.exports = {
  collectCoverage: true,
  collectCoverageFrom: ['src/**/*.{ts,js}'],
  coverageThreshold: {
    global: {
      branches: 0,
      functions: 0,
      lines: 0
    }
  },
  testEnvironment: 'jsdom',
  preset: 'react-native',
  coverageReporters: [
    'json',
    'lcov',
    'text',
    'text-summary',
    'json-summary',
    'cobertura'
  ],
  moduleFileExtensions: ['ts', 'tsx', 'js', 'jsx', 'json', 'node'],
  transform: {
    '^.+\\.(jsx?)$': '<rootDir>/node_modules/react-native/jest/preprocessor.js',
    '^.+\\.tsx?$': 'ts-jest'
  },
  testRegex: '/tst/.*\\.(test|spec)\\.(ts|tsx|js)$',
  transformIgnorePatterns: [
    'node_modules/(?!(jest-)?react-native|react-clone-referenced-element)',
    '<rootDir>/build',
    '<rootDir>/.tmp',
    '<rootDir>/.yarn-cache'
  ],
  testPathIgnorePatterns: [
    '/node_modules/',
    '<rootDir>/.tmp',
    '<rootDir>/build',
    '<rootDir>/.yarn-cache',
    '<rootDir>/offline-module-cache'
  ],
  modulePathIgnorePatterns: [
    '<rootDir>/offline-module-cache',
    '<rootDir>/.yarn-cache',
    '<rootDir>/build',
    '<rootDir>/.tmp'
  ],
  roots: ['<rootDir>/src/', '<rootDir>/tst/', '<rootDir>/node_modules/'],
  moduleNameMapper: {
    '^[./a-zA-Z0-9$_-]+\\.(bmp|gif|jpg|jpeg|png|psd|svg|webp)$':
      'RelativeImageStub',
    '^~/(.*)': '<rootDir>/src/$1'
  }
};
