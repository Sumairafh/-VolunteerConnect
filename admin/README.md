# 🛡️ Volunteer Connect — Web Admin Portal

A modern, responsive, web-based Admin Dashboard for **Volunteer Connect**. Designed to be easily opened and run directly inside **VS Code** with zero setup or external installation required.

---

## 🚀 How to Run in VS Code (Choose any method)

### Method 1: VS Code "Live Server" Extension (Easiest — 1 Click)
1. In VS Code, install the extension **"Live Server"** (by *Ritwick Dey*) from the Extensions panel (`Ctrl + Shift + X` or `Cmd + Shift + X`).
2. In the VS Code file explorer, right-click on `admin/index.html`.
3. Click **"Open with Live Server"** (or click **"Go Live"** in the bottom status bar).
4. The admin portal will immediately open at `http://127.0.0.1:5500/admin/index.html`!

---

### Method 2: Integrated Terminal (Built-in Python)
Open the integrated terminal in VS Code (`Ctrl + ~` or ``Cmd + ` ``) and run:
```bash
cd admin
python3 -m http.server 3000
```
*(On Windows, you can also use `python -m http.server 3000`)*  
Then open your browser to: **http://localhost:3000**

---

### Method 3: Using Node / NPM
In the VS Code terminal:
```bash
cd admin
npm start
```
*(Runs `npx serve . -l 3000` instantly without requiring any local dependencies)*

---

### Method 4: Double Click directly
You can even double click `admin/index.html` to open it in Chrome, Edge, Safari, or Firefox without any server running!

---

## 🌟 Admin Dashboard Features

- **🔐 Authentication & Access Flow (Sign In & New User Registration)**:
  - **Secure Login Dialog**: Sign in with registered credentials, session persistence via LocalStorage.
  - **1-Click Quick Demo Access**: Quick chips to test as **👑 Super Admin**, **🏢 NGO Lead**, or **🤝 Volunteer Lead**.
  - **New User Registration Flow**: Allows new users to self-register with Full Name, Email, Password, Role (Super Admin / NGO / Volunteer), Phone, City, and CNIC.
  - **Live User Profile & Logout**: Topbar and sidebar show current user identity with 1-click logout.

- **👥 User Accounts Management (Full CRUD)**:
  - **Create**: Add new user accounts directly from the admin dashboard with assigned role and verification status.
  - **Read & Filter**: Search by name, email, phone, or CNIC. Filter by role (`SUPER_ADMIN`, `ORGANIZATION`, `VOLUNTEER`) and status (`ACTIVE`, `SUSPENDED`, `PENDING`).
  - **Edit**: Update user profiles, change role permissions, modify city/contact info, or reset passwords.
  - **Delete & Suspend**: 1-click account suspension / activation or permanent account deletion with safety safeguards (cannot delete your own active admin session).

- **📅 Event & Item Management (Full CRUD)**:
  - **Create**: Publish new volunteer events and community items with category, organization, capacity, date/time, and address.
  - **Edit**: Modify title, category, city, schedule, address, briefing, and status directly in the edit modal.
  - **Delete**: Remove cancelled or invalid events/items with cascading application updates.
  - **Status Toggle**: 1-click toggle between `ACTIVE`, `FULL`, `COMPLETED`, and `CANCELLED`.
  - **Capacity Tracker**: Dynamic fill-rate progress bar tracking volunteers accepted vs. total slots.

- **📊 Comprehensive Analytics Overview**:
  - Live KPIs: Total Volunteers, Active Events, Verified NGOs, Applications, Impact Hours, and Safety Reports.
  - Interactive Chart.js graphs tracking monthly volunteer signups, community hours, and focus area distribution.
  - Action-required alert queue for pending organization verifications.

- **🏢 Organization & NGO Verification**:
  - Review NGO registration details, official contact info, and websites.
  - 1-click **Approve** or **Reject** actions.

- **👥 Volunteers Directory**:
  - Complete user list with City, Contact, and Key Skills tags.
  - Masked CNIC representation (`42101-*******-3`) protecting identity privacy.
  - Track completed events and verified community hours.

- **📝 Application Oversight**:
  - Track applications across all events.
  - Enforces the mobile app safety rule: **Maximum 2 active applications per volunteer**.
  - Admin override buttons (Accept, Reject, Reset).

- **🛡️ Safety & Community Moderation**:
  - Triage queue for user reports and safety concerns.
  - Triage actions (Resolve, Dismiss).

- **📢 Broadcast Announcements**:
  - Send platform-wide push notifications and emergency relief appeals.

- **💾 Data Export & Cloud Sync**:
  - Export Events, Users, Volunteers, Organizations, and Applications directly to **CSV**.
  - Download full system backup in **JSON**.
  - Toggle between Local Synced State and Live Firebase Firestore connection.

---

## 🔑 Demo Login Credentials
| Role | Email | Password |
|---|---|---|
| **Super Admin** | `admin@volunteerconnect.org` | `admin` |
| **NGO Lead** | `coordinator@greenfuture.org` | `org` |
| **Volunteer** | `ahmed.khan@connect.org` | `user` |
*(Or simply click any of the 1-Click Quick Demo chips on the login screen!)*
