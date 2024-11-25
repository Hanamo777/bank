<template>
  <div class="main-container">
    <!-- Header -->
    <Header></Header>
    <!-- Main Content -->
    <main>
      <div class="transaction-container">
        <h2 class="section-title">{{ $t('transaction.title') }}</h2>
        <table>
          <thead>
            <tr>
              <th>{{ $t('transaction.table.dateTime') }}</th>
              <th>{{ $t('transaction.table.type') }}</th>
              <th>{{ $t('transaction.table.accountHolder') }}</th>
              <th>{{ $t('transaction.table.amount') }}</th>
              <th>{{ $t('transaction.table.balance') }}</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(transaction, index) in transactions" :key="index">
              <td>{{ formatDateTime(transaction.transactionTime) }}</td>
              <td :class="getTransactionType(transaction)">
                {{
                  $t(
                    isDeposit(transaction)
                      ? 'transaction.types.deposit'
                      : 'transaction.types.withdraw'
                  )
                }}
              </td>
              <td>
                {{
                  isDeposit(transaction)
                    ? transaction.senderName
                    : transaction.receiverName
                }}
              </td>
              <td>{{ formatAmount(transaction.amount) }}원</td>
              <td>
                {{
                  formatAmount(
                    isDeposit(transaction)
                      ? transaction.receiverBalanceAfter
                      : transaction.senderBalanceAfter
                  )
                }}원
              </td>
            </tr>
          </tbody>
        </table>
        <button class="home-button" @click="goHome">
          {{ $t('transaction.buttons.home') }}
        </button>
      </div>
    </main>
  </div>
</template>

<script>
import Header from '@/components/Header.vue';
import { api } from '@/api';
import { useI18n } from 'vue-i18n';

export default {
  name: 'TransactionHistoryView',
  components: {
    Header,
  },
  setup() {
    const { t } = useI18n();
    return { t };
  },
  data() {
    return {
      transactions: [],
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
    this.getTransactions();
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
    formatDateTime(dateTime) {
      return new Date(dateTime).toLocaleString();
    },
    formatAmount(amount) {
      return amount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    },
    isDeposit(transaction) {
      return transaction.receiverAccount === this.myAccount;
    },
    getTransactionType(transaction) {
      return this.isDeposit(transaction) ? 'deposit' : 'withdraw';
    },
    async getTransactions() {
      try {
        const response = await api.get(`/account/history/${this.myAccount}`);
        this.transactions = response.data;
      } catch (error) {
        console.error('거래내역 조회 실패:', error);
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

.transaction-container {
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
