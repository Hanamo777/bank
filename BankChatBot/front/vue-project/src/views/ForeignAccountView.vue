<template>
  <Header></Header>
  <div class="account-container">
    <div class="account-box">
      <h2 class="section-title">{{ userName }}님의 외화계좌</h2>
      <div class="info-box">
        <div class="account-info">
          <h3>계좌번호 : {{ accountNumber }}</h3>
          <h3>잔액</h3>
          <p class="balance">
            {{ balance.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ",") }} USD
          </p>
        </div>
      </div>
      <div class="button-group">
        <button class="transfer-button" @click="showTransfer">계좌 이체</button>
        <button class="history-button" @click="showHistory">거래 내역</button>
        <!-- <button class="game-button" @click="showGame">King Sejong</button> -->
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header.vue";
import { api } from "@/api";

export default {
  name: "AccountView",
  components: {
    Header,
  },
  data() {
    return {
      userName: "",
      balance: 0,
      accountNumber: 0,
      userIsActive: 1,
    };
  },
  created() {
    const user = JSON.parse(localStorage.getItem("user"));
    if (!user) {
      this.$router.push("/");
      return;
    }
    this.userName = user.name;
    this.accountNumber = user.accountNumber;
    this.getBalance();
    this.userIsActive = user.isActive;
  },
  methods: {
    async getBalance() {
      try {
        const response = await api.get(
          `/account/foreign/${this.accountNumber}`
        );
        this.balance = response.data.balance;
      } catch (error) {
        console.error("계좌 조회 실패:", error);
      }
    },
    showTransfer() {
      if (this.userIsActive == 0) {
        alert("해당 계좌는 비활성화 상태입니다.");
      } else {
        this.$router.push("/transfer");
      }
    },
    showHistory() {
      this.$router.push("/history");
    },
    showGame() {
      this.$router.push("/game");
    },
    logout() {
      localStorage.removeItem("user");
      this.$router.push("/");
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
.game-button {
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
.game-button {
  background-color: #e9ecef;
  color: #495057;
}

.history-button:hover,
.logout-button:hover,
.game-button:hover {
  background-color: #dee2e6;
}
</style>
