```vue
<template>
  <div class="main-container">
    <Header></Header>
    <main>
      <div class="transfer-container">
        <h2 class="section-title">Transfer</h2>
        <div class="transfer-form">
          <div class="info-box">
            <div class="balance-info">
              Current Balance: ${{ balance.toLocaleString() }}
            </div>
          </div>

          <div class="input-group">
            <label>Recipient Account Number</label>
            <input
              type="number"
              v-model="receiverAccount"
              placeholder="Enter recipient account number"
            />
          </div>

          <div class="input-group">
            <label>Transfer Amount</label>
            <input
              type="number"
              v-model="amount"
              placeholder="Enter transfer amount"
            />
          </div>

          <div class="fee-details">
            <div class="fee-info">Fee: $500</div>
            <div class="total-info">
              Total Amount: ${{ (Number(amount) + 500).toLocaleString() }}
            </div>
          </div>

          <div class="button-group">
            <button class="transfer-button" @click="transfer">Transfer</button>
            <button class="home-button" @click="goHome">Home</button>
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
  name: 'TransferView',
  components: {
    Header,
  },
  data() {
    return {
      balance: 0,
      receiverAccount: '',
      amount: '',
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
    this.getBalance();
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
    async getBalance() {
      try {
        const response = await api.get(`/account/${this.myAccount}`);
        this.balance = response.data.balance;
      } catch (error) {
        console.error('잔액 조회 실패:', error);
      }
    },
    async transfer() {
      try {
        // Check sender account status
        const senderInfo = await api.get(`/user/${this.myAccount}`);
        if (senderInfo.data.isActive === 0) {
          alert('Account is deactivated. Transfer cannot be processed.');
          return;
        }

        // Check recipient account information
        const accountInfo = await api.get(`/account/${this.receiverAccount}`);
        const userInfo = await api.get(`/user/${this.receiverAccount}`);

        if (this.myAccount == this.receiverAccount) {
          alert('Cannot transfer to your own account.');
        } else if (userInfo.data.isActive == 0) {
          alert('Transfer failed. Recipient account is deactivated.');
        } else if (
          confirm(
            `Would you like to transfer $${Number(
              this.amount
            ).toLocaleString()} to ${accountInfo.data.userName}?\n(Total $${(
              Number(this.amount) + 500
            ).toLocaleString()} including $500 fee)`
          )
        ) {
          const response = await api.post('/account/transfer', {
            senderAccount: this.myAccount,
            receiverAccount: Number(this.receiverAccount),
            amount: Number(this.amount),
          });

          if (response.data === 'success') {
            alert('Transfer completed successfully!');
            this.$router.push('/');
          } else {
            alert('Transfer failed');
          }
        }
      } catch (error) {
        console.error('Transfer failed:', error);
        alert('Invalid account number.');
      }
    },
    goHome() {
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

.transfer-container {
  max-width: 600px;
  margin: 2rem auto;
  padding: 0 2rem;
}

.section-title {
  font-size: 1.5rem;
  font-weight: 600;
  margin-bottom: 1.5rem;
  color: #333;
}

.transfer-form {
  background-color: white;
  padding: 2rem;
  border-radius: 0.5rem;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.info-box {
  background-color: #f8f9fa;
  padding: 1.5rem;
  border-radius: 0.5rem;
  margin-bottom: 1.5rem;
}

.balance-info {
  font-size: 1.2rem;
  font-weight: 500;
  color: #00857e;
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

.fee-details {
  background-color: #f8f9fa;
  padding: 1rem;
  border-radius: 0.5rem;
  margin: 1.5rem 0;
}

.fee-info,
.total-info {
  padding: 0.5rem;
  text-align: right;
}

.total-info {
  font-weight: 500;
  color: #00857e;
}

.button-group {
  display: flex;
  gap: 1rem;
  margin-top: 2rem;
}

.transfer-button,
.home-button {
  flex: 1;
  padding: 0.75rem;
  border: none;
  border-radius: 0.5rem;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.2s;
}

.transfer-button {
  background-color: #00857e;
  color: white;
}

.transfer-button:hover {
  background-color: #006c64;
}

.home-button {
  background-color: #e9ecef;
  color: #495057;
}

.home-button:hover {
  background-color: #dee2e6;
}
</style>
```
