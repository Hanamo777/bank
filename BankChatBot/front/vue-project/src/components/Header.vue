<template>
  <header class="header">
    <div class="top-menu">
      <div class="left-menu">
        <span>Corporate Banking</span>
        <span>Hana Card</span>
        <span>About Bank</span>
        <span>Careers</span>
      </div>
      <div class="right-menu">
        <template v-if="isLoggedIn">
          <span>{{ userName }}</span>
          <span @click="handleLogout">Logout</span>
        </template>
        <template v-else>
          <router-link to="/login">Login</router-link>
        </template>
        <span>Authentication Center</span>
        <span>Language</span>
      </div>
    </div>

    <nav class="main-nav">
      <div class="logo">
        <span class="text-xl font-bold text-green-600" @click="goMain"
          >Hana Bank</span
        >
      </div>
      <div class="nav-items">
        <span @click="goAccount">Account</span>
        <span class="menu-card" @click="handleTransfer">Transfer</span>
        <span>Public Utilities</span>
        <span>Foreign Exchange</span>
        <span>Financial Products</span>
      </div>
      <div class="nav-icons">
        <button class="chat-btn" @click="openChat">Ask Hana Bot</button>
        <button class="search-btn">Search</button>
        <button class="menu-btn">Menu</button>
      </div>
    </nav>
    <chat-popup ref="chatPopup" />
  </header>
</template>

<script>
import ChatPopup from './ChatPopup.vue';
export default {
  name: 'Header',
  components: {
    ChatPopup,
  },
  data() {
    return {
      isLoggedIn: false,
      userName: '',
      userId: '',
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
        alert('Login required for this service.');
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
        alert('Login required for this service.');
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
</style>
