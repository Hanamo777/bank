<template>
  <div class="main-container">
    <Header></Header>
    <main>
      <div class="login-container">
        <div class="login-box">
          <h2 class="section-title">{{ $t('login.title') }}</h2>
          <div class="login-form">
            <div class="input-group">
              <label>{{ $t('login.form.userId') }}</label>
              <input
                type="text"
                v-model="userId"
                :placeholder="$t('login.form.userIdPlaceholder')"
              />
            </div>

            <div class="input-group">
              <label>{{ $t('login.form.password') }}</label>
              <input
                type="password"
                v-model="password"
                :placeholder="$t('login.form.passwordPlaceholder')"
              />
            </div>

            <div class="button-group">
              <button class="login-button" @click="login">
                {{ $t('login.buttons.login') }}
              </button>
              <button class="register-button" @click="showRegister">
                {{ $t('login.buttons.register') }}
              </button>
            </div>

            <div class="help-links">
              <span>{{ $t('login.help.findId') }}</span>
              <span>{{ $t('login.help.findPassword') }}</span>
              <span>{{ $t('login.help.certificate') }}</span>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import Header from '@/components/Header.vue';
import { api } from '@/api';
import { useI18n } from 'vue-i18n';

export default {
  name: 'LoginView',
  components: {
    Header,
  },
  setup() {
    const { t } = useI18n();
    return { t };
  },
  data() {
    return {
      userId: '',
      password: '',
    };
  },
  methods: {
    async login() {
      try {
        const response = await api.post('/user/login', {
          userId: this.userId,
          password: this.password,
        });
        if (response.data) {
          localStorage.setItem('user', JSON.stringify(response.data));
          if (response.data.userId == 0) {
            this.$router.push('/admin');
          } else {
            this.$router.push('/');
          }
        } else {
          alert(this.$t('login.messages.failure'));
        }
      } catch (error) {
        console.error(this.$t('login.messages.error'), error);
        alert(this.$t('login.messages.failure'));
      }
    },
    showRegister() {
      this.$router.push('/register');
    },
  },
};
</script>

<style scoped>
.main-container {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto,
    'Helvetica Neue', Arial, sans-serif;
}

.header {
  padding: 0 2rem;
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

.login-container {
  max-width: 500px;
  margin: 4rem auto;
  padding: 0 2rem;
}

.login-box {
  background-color: white;
  padding: 2rem;
  border-radius: 0.5rem;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.section-title {
  font-size: 1.5rem;
  font-weight: 600;
  margin-bottom: 2rem;
  color: #333;
  text-align: center;
}

.input-group {
  margin-bottom: 1.5rem;
}

.input-group label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: 500;
  color: #495057;
}

.input-group input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #dee2e6;
  border-radius: 0.25rem;
  font-size: 1rem;
}

.button-group {
  display: flex;
  gap: 1rem;
  margin: 2rem 0;
}

.login-button,
.register-button {
  flex: 1;
  padding: 0.75rem;
  border: none;
  border-radius: 0.5rem;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.2s;
}

.login-button {
  background-color: #00857e;
  color: white;
}

.login-button:hover {
  background-color: #006c64;
}

.register-button {
  background-color: #e9ecef;
  color: #495057;
}

.register-button:hover {
  background-color: #dee2e6;
}

.help-links {
  display: flex;
  justify-content: center;
  gap: 2rem;
  margin-top: 1.5rem;
  color: #6c757d;
  font-size: 0.875rem;
}

.help-links span {
  cursor: pointer;
}

.help-links span:hover {
  color: #00857e;
  text-decoration: underline;
}
</style>
```
