<template>
  <header class="header">
    <div class="top-menu">
      <div class="left-menu">
        <span>{{ $t('header.menu.corporate') }}</span>
        <span>{{ $t('header.menu.card') }}</span>
        <span>{{ $t('header.menu.about') }}</span>
        <span>{{ $t('header.menu.career') }}</span>
      </div>
      <div class="right-menu">
        <template v-if="isLoggedIn">
          <span>{{ $t('header.auth.welcome', { name: userName }) }}</span>
          <span @click="handleLogout">{{ $t('header.auth.logout') }}</span>
        </template>
        <template v-else>
          <router-link to="/login">{{ $t('header.auth.login') }}</router-link>
        </template>
        <span>{{ $t('header.menu.certification') }}</span>
        <div class="language-selector">
          <span @click="toggleLanguageMenu">Language</span>
          <div v-if="showLanguageMenu" class="language-dropdown">
            <div
              @click="changeLanguage('ko')"
              :class="{ active: currentLocale === 'ko' }"
            >
              한국어
            </div>
            <div
              @click="changeLanguage('en')"
              :class="{ active: currentLocale === 'en' }"
            >
              English
            </div>
            <div
              @click="changeLanguage('ja')"
              :class="{ active: currentLocale === 'ja' }"
            >
              日本語
            </div>
          </div>
        </div>
      </div>
    </div>

    <nav class="main-nav">
      <div class="logo">
        <span class="text-xl font-bold text-green-600" @click="goMain">{{
          $t('header.nav.bank')
        }}</span>
      </div>
      <div class="nav-items">
        <span @click="goAccount">{{ $t('header.nav.inquiry') }}</span>
        <span class="menu-card" @click="handleTransfer">{{
          $t('header.nav.transfer')
        }}</span>
        <span>{{ $t('header.nav.bills') }}</span>
        <span>{{ $t('header.nav.forex') }}</span>
        <span>{{ $t('header.nav.products') }}</span>
      </div>
      <div class="nav-icons">
        <button class="chat-btn" @click="openChat">
          {{ $t('header.chat.ask') }}
        </button>
        <button class="search-btn">{{ $t('header.nav.search') }}</button>
        <button class="menu-btn">{{ $t('header.nav.menu') }}</button>
      </div>
    </nav>
    <chat-popup ref="chatPopup" />
  </header>
</template>

<script>
import ChatPopup from './ChatPopup.vue';
import { useI18n } from 'vue-i18n';

export default {
  name: 'Header',
  components: {
    ChatPopup,
  },
  setup() {
    const { locale } = useI18n();
    return { locale };
  },
  data() {
    return {
      isLoggedIn: false,
      userName: '',
      userId: '',
      showLanguageMenu: false,
      currentLocale: 'ko',
    };
  },
  created() {
    this.checkLoginStatus();
  },
  methods: {
    checkLoginStatus() {
      const user = JSON.parse(localStorage.getItem('user'));
      if (user) {
        this.isLoggedIn = true;
        this.userName = user.name;
        this.userId = user.userId;
      }
    },
    handleLogout() {
      const userId = JSON.parse(localStorage.getItem('user')).userId;
      localStorage.removeItem(`chatMessages_${userId}`);
      localStorage.removeItem(`chatOpen_${userId}`);
      localStorage.removeItem('user');
      this.isLoggedIn = false;
      this.userName = '';
      this.$router.push('/login');
    },
    handleTransfer() {
      if (!this.isLoggedIn) {
        alert(this.$t('messages.loginRequired'));
        this.$router.push('/login');
        return;
      }
      this.$router.push('/transfer');
    },
    goMain() {
      this.$router.push('/');
    },
    goAccount() {
      if (!this.isLoggedIn) {
        alert(this.$t('messages.loginRequired'));
        this.$router.push('/login');
        return;
      }
      if (this.userId == 0) {
        this.$router.push('/admin');
      } else {
        this.$router.push('/account');
      }
    },
    openChat() {
      this.$refs.chatPopup.isOpen = true;
    },
    toggleLanguageMenu() {
      this.showLanguageMenu = !this.showLanguageMenu;
    },
    changeLanguage(lang) {
      this.currentLocale = lang;
      this.locale = lang;
      this.showLanguageMenu = false;
    },
  },
};
</script>

<style scoped>
.header {
  padding: 0 2rem;
}
.logo {
  cursor: pointer;
}
.top-menu {
  display: flex;
  justify-content: space-between;
  padding: 0.5rem 0;
  font-size: 0.875rem;
  color: #777;
}

.left-menu span,
.right-menu span {
  margin: 0 1rem;
  cursor: pointer;
}

.main-nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 0;
  border-bottom: 1px solid #eee;
}

.nav-items span {
  margin: 0 1.5rem;
  font-weight: 500;
  cursor: pointer;
}

.nav-icons button {
  margin-left: 1rem;
}

.chat-btn {
  background-color: #00857e;
  color: white;
  padding: 0.5rem 1rem;
  border-radius: 1.5rem;
  border: none;
  cursor: pointer;
}

.search-btn,
.menu-btn {
  background: none;
  border: none;
  cursor: pointer;
}

.language-selector {
  position: relative;
  display: inline-block;
  cursor: pointer;
}

.language-dropdown {
  position: absolute;
  top: 100%;
  right: 0;
  background: white;
  border: 1px solid #eee;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}

.language-dropdown div {
  padding: 8px 16px;
  white-space: nowrap;
  cursor: pointer;
}

.language-dropdown div:hover {
  background: #f5f5f5;
}

.language-dropdown div.active {
  background: #e8f5e9;
  color: #00857e;
}
</style>
