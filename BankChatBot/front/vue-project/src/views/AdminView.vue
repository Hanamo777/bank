<template>
  <Header></Header>
  <div class="account-container">
    <div class="account-box">
      <h2 class="section-title">{{ $t('admin.title') }}</h2>
      <div class="info-box">
        <div class="account-info">
          <h3>{{ $t('admin.accountNumber', { number: accountNumber }) }}</h3>
          <h3>{{ $t('admin.balance') }}</h3>
          <p class="balance">
            {{ $t('admin.amount', { amount: balance.toLocaleString() }) }}
          </p>
        </div>
      </div>
      <div class="button-group">
        <button class="transfer-button" @click="showTransfer">
          {{ $t('admin.buttons.transfer') }}
        </button>
        <button class="history-button" @click="showHistory">
          {{ $t('admin.buttons.history') }}
        </button>
        <button class="activetoggle-button" @click="showUsers">
          {{ $t('admin.buttons.manage') }}
        </button>
        <!-- <button class="logout-button" @click="logout">{{ $t('admin.buttons.logout') }}</button> -->
      </div>
    </div>
  </div>
</template>

<script>
import Header from '@/components/Header.vue';
import { api } from '@/api';
import { useI18n } from 'vue-i18n';

export default {
  name: 'AccountView',
  components: {
    Header,
  },
  setup() {
    const { t } = useI18n();
    return { t };
  },
  data() {
    return {
      userName: '',
      balance: 0,
      accountNumber: 0,
    };
  },
  created() {
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) {
      this.$router.push('/');
      return;
    }
    this.userName = user.name;
    this.accountNumber = user.accountNumber;
    this.getBalance();
  },
  methods: {
    async getBalance() {
      try {
        const response = await api.get(`/account/${this.accountNumber}`);
        this.balance = response.data.balance;
      } catch (error) {
        console.error(this.$t('admin.messages.error'), error);
      }
    },
    showTransfer() {
      this.$router.push('/transfer');
    },
    showHistory() {
      this.$router.push('/history');
    },
    showUsers() {
      this.$router.push('/userlist');
    },
    logout() {
      localStorage.removeItem('user');
      this.$router.push('/');
    },
  },
};
</script>

<style scoped>
.account-container {
  max-width: 600px;
  margin: 4rem auto;
  padding: 0 2rem;
}

.account-box {
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

.info-box {
  background-color: #f8f9fa;
  padding: 1.5rem;
  border-radius: 0.5rem;
  margin-bottom: 1.5rem;
}

.account-info {
  text-align: center;
}

.account-info h3 {
  margin-bottom: 1rem;
  color: #495057;
}

.balance {
  font-size: 1.5rem;
  font-weight: 600;
  color: #00857e;
}

.button-group {
  display: flex;
  gap: 1rem;
  margin-top: 2rem;
}

.transfer-button,
.history-button,
.logout-button,
.activetoggle-button,
.logout-button {
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

.history-button,
.logout-button,
.activetoggle-button {
  background-color: #e9ecef;
  color: #495057;
}

.history-button:hover,
.logout-button:hover,
.activetoggle-button:hover {
  background-color: #dee2e6;
}
</style>
