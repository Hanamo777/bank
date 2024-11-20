<template>
  <div class="main-container">
    <Header></Header>
    <main>
      <!-- Slider Section -->
      <section class="slider-section">
        <div class="slider-content">
          <div class="event-tag">Event</div>
          <h1>Supporting Students<br />Send Your Heart with Money</h1>
          <p>Send a supportive message card<br />and enjoy dinner together!</p>
          <button class="cta-button">Learn More</button>
        </div>
        <div class="slider-controls">
          <span>1 / 6</span>
          <button class="dots-menu">···</button>
        </div>
      </section>

      <!-- Quick Menu Grid -->
      <section class="quick-menu-grid">
        <div class="menu-card" @click="handleInquiry">
          <HomeIcon class="icon" />
          <span>Transaction History</span>
        </div>
        <div class="menu-card" @click="handleTransfer">
          <TransferIcon class="icon" />
          <span>Transfer</span>
        </div>
        <div class="menu-card">
          <BillIcon class="icon" />
          <span>Public Utilities</span>
        </div>
        <div class="menu-card">
          <ExchangeIcon class="icon" />
          <span>Exchange Rate</span>
        </div>
        <div class="menu-card">
          <OpenBankingIcon class="icon" />
          <span>Open Banking</span>
        </div>
        <div class="menu-card">
          <SecurityIcon class="icon" />
          <span>Authentication Center</span>
        </div>
        <div class="menu-card">
          <QuickViewIcon class="icon" />
          <span>Quick View</span>
        </div>
        <div class="menu-card">
          <CustomerServiceIcon class="icon" />
          <span>Customer Service</span>
        </div>
      </section>

      <!-- Bottom Menu -->
      <section class="bottom-menu">
        <div class="menu-item">
          <h3>Savings</h3>
          <p>Tips for Building Wealth</p>
        </div>
        <div class="menu-item">
          <h3>Funds</h3>
          <p>Fund Rankings at a Glance</p>
        </div>
        <div class="menu-item">
          <h3>Exchange Wallet</h3>
          <p>Smart Currency Exchange</p>
        </div>
        <div class="menu-item">
          <h3>Retirement Pension</h3>
          <p>For a Secure Retirement</p>
        </div>
        <div class="menu-item">
          <h3>Loans</h3>
          <p>3-Minute Limit Check!</p>
        </div>
      </section>

      <!-- News Section -->
      <section class="news-section">
        <div class="news-header">
          <h3>News</h3>
          <button>View More</button>
        </div>
        <ul class="news-list">
          <li>
            <span class="news-title"
              >Notice: Temporary Service Suspension for National Tax Service
              MyData Service</span
            >
            <span class="news-date">2024-11-15</span>
          </li>
          <li>
            <span class="news-title"
              >Notice: Partial Financial Transaction Service Suspension</span
            >
            <span class="news-date">2024-11-15</span>
          </li>
          <li>
            <span class="news-title"
              >Announcement: Selection of Hana Bank Offline Event Agency</span
            >
            <span class="news-date">2024-11-15</span>
          </li>
        </ul>
      </section>
    </main>
  </div>
</template>

<script>
import Header from '@/components/Header.vue';
import {
  HomeIcon,
  TransferIcon,
  BillIcon,
  ExchangeIcon,
  OpenBankingIcon,
  SecurityIcon,
  QuickViewIcon,
  CustomerServiceIcon,
} from '@/components/Icons.vue';

export default {
  name: 'HanabankView',
  components: {
    HomeIcon,
    TransferIcon,
    BillIcon,
    ExchangeIcon,
    OpenBankingIcon,
    SecurityIcon,
    QuickViewIcon,
    CustomerServiceIcon,
    Header,
  },
  data() {
    return {
      isLoggedIn: false,
      userName: '',
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
      }
    },
    handleLogout() {
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
    handleInquiry() {
      if (!this.isLoggedIn) {
        alert('Login required for this service.');
        this.$router.push('/login');
        return;
      }
      this.$router.push('/history');
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

.slider-section {
  position: relative;
  background-color: #a2e9b4;
  padding: 3rem 2rem;
  min-height: 400px;
  background-image: url('@/assets/chalkboard.jpg');
  background-size: cover;
  background-position: center;
  color: white;
}

.slider-content h1 {
  font-size: 2.5rem;
  margin: 1rem 0;
  line-height: 1.2;
}

.event-tag {
  display: inline-block;
  padding: 0.25rem 1rem;
  background-color: rgba(0, 255, 98, 0.2);
  border-radius: 1rem;
}

.cta-button {
  margin-top: 2rem;
  padding: 0.75rem 2rem;
  background-color: white;
  color: #333;
  border: none;
  border-radius: 2rem;
  cursor: pointer;
}

.slider-controls {
  position: absolute;
  bottom: 2rem;
  left: 2rem;
  display: flex;
  align-items: center;
  gap: 1rem;
}

.quick-menu-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 1rem;
  padding: 2rem;
}

.menu-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
  padding: 1rem;
  cursor: pointer;
}

.icon {
  width: 2rem;
  height: 2rem;
  color: #00857e;
}

.bottom-menu {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  background-color: #f5f5f5;
  padding: 2rem;
}

.menu-item {
  text-align: center;
  padding: 1rem;
  cursor: pointer;
}

.menu-item h3 {
  font-weight: 500;
  margin-bottom: 0.5rem;
}

.menu-item p {
  color: #777;
  font-size: 0.875rem;
}

.news-section {
  padding: 2rem;
}

.news-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.news-header button {
  background: none;
  border: none;
  color: #777;
  cursor: pointer;
}

.news-list {
  list-style: none;
  padding: 0;
}

.news-list li {
  display: flex;
  justify-content: space-between;
  padding: 0.75rem 0;
  border-bottom: 1px solid #eee;
  cursor: pointer;
}

.news-date {
  color: #777;
}
</style>
