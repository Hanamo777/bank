import { createI18n } from 'vue-i18n';
import ko from './ko.json';
import en from './en.json';
import ja from './ja.json';

export default createI18n({
  legacy: false,
  locale: 'en',
  fallbackLocale: 'en',
  messages: {
    ko,
    en,
    ja,
  },
});
