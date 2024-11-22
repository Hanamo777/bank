<template>
  <div class="main-container">
    <Header></Header>
    <main>
      <div class="transfer-container">
        <h2 class="section-title">{{ $t('transfer.title') }}</h2>
        <div class="transfer-form">
          <div class="info-box">
            <div class="balance-info">
              {{ $t('transfer.balance', { amount: balance.toLocaleString() }) }}
            </div>
          </div>

          <div class="input-group">
            <label>{{ $t('transfer.form.receiverAccount.label') }}</label>
            <input
              type="number"
              v-model="receiverAccount"
              :placeholder="$t('transfer.form.receiverAccount.placeholder')"
            />
          </div>

          <div class="input-group">
            <label>{{ $t('transfer.form.amount.label') }}</label>
            <input
              type="number"
              v-model="amount"
              :placeholder="$t('transfer.form.amount.placeholder')"
            />
          </div>

          <div class="fee-details">
            <div class="fee-info">
              {{ $t('transfer.fee.label', { amount: fee }) }}
            </div>
            <div class="total-info">
              {{
                $t('transfer.fee.total', {
                  amount: (Number(amount) + fee).toLocaleString(),
                })
              }}
            </div>
          </div>

          <div class="button-group">
            <button class="transfer-button" @click="transfer">
              {{ $t('transfer.buttons.transfer') }}
            </button>
            <button class="home-button" @click="goHome">
              {{ $t('transfer.buttons.home') }}
            </button>
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
  name: 'TransferView',
  components: {
    Header,
  },
  setup() {
    const { t } = useI18n();
    return { t };
  },
  data() {
    return {
      balance: 0,
      receiverAccount: '',
      amount: '',
      myAccount: 0,
      isLoggedIn: false,
      userName: '',
      fee: 500,
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
    this.checkGameResult();
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
        console.error(this.$t('transfer.messages.balanceError'), error);
      }
    },
    async transfer() {
      try {
        // 발신자 계좌 상태 확인
        const senderInfo = await api.get(`/user/${this.myAccount}`);
        if (senderInfo.data.isActive === 0) {
          alert(this.$t('transfer.messages.inactive'));
          return;
        }

        // 수신자 계좌 정보 확인
        const accountInfo = await api.get(`/account/${this.receiverAccount}`);
        const userInfo = await api.get(`/user/${this.receiverAccount}`);

        if (this.myAccount == this.receiverAccount) {
          alert(this.$t('transfer.messages.selfTransfer'));
        } else if (userInfo.data.isActive == 0) {
          alert(this.$t('transfer.messages.receiverInactive'));
        } else if (!accountInfo.data.userName) {
          alert(this.$t('transfer.messages.noAccount'));
        } else if (
          confirm(
            this.$t('transfer.messages.confirm', {
              name: accountInfo.data.userName,
              amount: Number(this.amount).toLocaleString(),
              fee: this.fee,
              total: (Number(this.amount) + this.fee).toLocaleString(),
            })
          )
        ) {
          const response = await api.post('/account/transfer', {
            senderAccount: this.myAccount,
            receiverAccount: Number(this.receiverAccount),
            amount: Number(this.amount),
            fee: Number(this.fee),
          });
          if (response.data === 'success') {
            alert(this.$t('transfer.messages.success'));
            this.$router.push('/');
          } else {
            alert(this.$t('transfer.messages.fail'));
          }
        }
      } catch (error) {
        console.error(this.$t('transfer.messages.fail'), error);
        alert(this.$t('transfer.messages.invalidAccount'));
      }
    },
    async checkGameResult() {
      const result = this.$route.query.result;
      console.log(result);
      if (result === undefined) return;
      else {
        this.fee = result === 'true' ? 50 : 500;
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
