<template>
  <div class="main-container">
    <!-- Header -->
    <Header></Header>
    <!-- Main Content -->
    <main>
      <div class="register-container">
        <div class="register-box">
          <h2 class="section-title">Sign Up</h2>
          <div class="register-form">
            <div class="input-group">
              <label>ID</label>
              <input type="text" v-model="userId" placeholder="Enter your ID" />
            </div>

            <div class="input-group">
              <label>Password</label>
              <input
                type="password"
                v-model="password"
                placeholder="Enter your password"
              />
              <p class="input-hint">
                8-20 characters with letters, numbers, and special characters
              </p>
            </div>

            <div class="input-group">
              <label>Name</label>
              <input type="text" v-model="name" placeholder="Enter your name" />
            </div>
            <div class="info-box">
              <h3>Registration Notes</h3>
              <ul>
                <li>
                  An account will be automatically created upon registration.
                </li>
                <li>Referral verification will be processed if provided.</li>
                <li>
                  You will be redirected to the login page after completion.
                </li>
              </ul>
            </div>

            <div class="button-group">
              <button class="register-button" @click="register">Sign Up</button>
              <button class="cancel-button" @click="goToLogin">Cancel</button>
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
export default {
  name: 'RegisterView',
  components: {
    Header,
  },
  data() {
    return {
      userId: '',
      password: '',
      name: '',
    };
  },
  methods: {
    async register() {
      try {
        const response = await api.post('/user/register', {
          userId: this.userId,
          password: this.password,
          name: this.name,
          accountNumber: 1, // Temporary set to 1
        });

        console.log('Registration response:', response.data.value);
        if (response.data === 'success') {
          this.$router.push('/referral-bonus');
        }
      } catch (error) {
        console.error('Registration failed:', error);
      }
    },
    goToLogin() {
      this.$router.push('/');
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

.register-container {
  max-width: 600px;
  margin: 4rem auto;
  padding: 0 2rem;
}

.register-box {
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

.optional {
  color: #6c757d;
  font-weight: normal;
  font-size: 0.875rem;
}

.input-group input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #dee2e6;
  border-radius: 0.25rem;
  font-size: 1rem;
}

.input-hint {
  margin-top: 0.25rem;
  font-size: 0.875rem;
  color: #6c757d;
}

.info-box {
  background-color: #f8f9fa;
  padding: 1.5rem;
  border-radius: 0.5rem;
  margin: 2rem 0;
}

.info-box h3 {
  font-size: 1rem;
  font-weight: 500;
  margin-bottom: 1rem;
  color: #495057;
}

.info-box ul {
  padding-left: 1.5rem;
  color: #6c757d;
}

.info-box li {
  margin-bottom: 0.5rem;
}

.button-group {
  display: flex;
  gap: 1rem;
  margin-top: 2rem;
}

.register-button,
.cancel-button {
  flex: 1;
  padding: 0.75rem;
  border: none;
  border-radius: 0.5rem;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.2s;
}

.register-button {
  background-color: #00857e;
  color: white;
}

.register-button:hover {
  background-color: #006c64;
}

.cancel-button {
  background-color: #e9ecef;
  color: #495057;
}

.cancel-button:hover {
  background-color: #dee2e6;
}
</style>
```
