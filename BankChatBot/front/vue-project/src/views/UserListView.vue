```vue
<template>
  <div class="main-container">
    <!-- Header -->
    <Header></Header>
    <!-- Main Content -->
    <main>
      <div class="userlist-container">
        <h2 class="section-title">User List</h2>
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Password</th>
              <th>Name</th>
              <th>Account Number</th>
              <th>Account Status</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(user, index) in users" :key="index">
              <td>{{ user.userId }}</td>
              <td>{{ user.password }}</td>
              <td>{{ user.name }}</td>
              <td>{{ user.accountNumber }}</td>
              <td @click="toggleActive(user)">
                {{ user.isActive ? 'Active' : 'Inactive' }}
              </td>
            </tr>
          </tbody>
        </table>
        <button class="home-button" @click="goHome">Home</button>
      </div>
    </main>
  </div>
</template>

<script>
import Header from '@/components/Header.vue';
import { api } from '@/api';

export default {
  name: 'UserListView',
  components: {
    Header,
  },
  data() {
    return {
      users: [],
      myAccount: 0,
      isLoggedIn: false,
      userName: '',
    };
  },
  created() {
    this.checkLoginStatus();
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) {
      this.$router.push('/');
      return;
    }
    this.myAccount = user.accountNumber;
    this.getUsers();
  },
  methods: {
    checkLoginStatus() {
      const user = JSON.parse(localStorage.getItem('user'));
      if (user) {
        this.isLoggedIn = true;
        this.userName = user.name;
      }
    },
    handleLogout() {
      localStorage.removeItem('user');
      this.isLoggedIn = false;
      this.userName = '';
      this.$router.push('/login');
    },
    async getUsers() {
      try {
        const response = await api.get(`/admin/users`);
        this.users = response.data;
      } catch (error) {
        console.error('Failed to fetch user list:', error);
      }
    },
    async toggleActive(user) {
      try {
        const newStatus = user.isActive ? 0 : 1;
        await api.put(`/admin/${user.userId}/status`, {
          isActive: newStatus,
        });
        user.isActive = newStatus;
      } catch (error) {
        console.log('Failed to change account status');
      }
    },
    goHome() {
      this.$router.push('/admin');
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

.userlist-container {
  max-width: 1200px;
  margin: 2rem auto;
  padding: 0 2rem;
}

.section-title {
  font-size: 1.5rem;
  font-weight: 600;
  margin-bottom: 1.5rem;
  color: #333;
}

table {
  width: 100%;
  border-collapse: collapse;
  background-color: white;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

th {
  background-color: #f8f9fa;
  padding: 1rem;
  text-align: center;
  font-weight: 600;
  color: #495057;
}

td {
  padding: 1rem;
  text-align: center;
  border-bottom: 1px solid #dee2e6;
}

.deposit {
  color: #00857e;
  font-weight: 500;
}

.withdraw {
  color: #dc3545;
  font-weight: 500;
}

.home-button {
  display: block;
  width: 200px;
  margin: 2rem auto;
  padding: 0.75rem 1.5rem;
  background-color: #00857e;
  color: white;
  border: none;
  border-radius: 0.5rem;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.2s;
}

.home-button:hover {
  background-color: #006c64;
}
</style>
```
