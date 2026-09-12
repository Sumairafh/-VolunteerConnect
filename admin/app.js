/**
 * Volunteer Connect - Web Admin Portal Engine
 * Standalone, zero-dependency browser app for VS Code Live Server / modern browsers
 */

// Initial Seed Data (matching Volunteer Connect Android models)
const DEFAULT_ORGS = [
  {
    organizationId: "org_201",
    name: "Green Future Foundation",
    description: "Registered non-profit empowering youth for environmental sustainability, tree plantation drives, and marine ecosystem cleanups across Pakistan.",
    location: "Clifton, Karachi",
    city: "Karachi",
    contactPhone: "+92 321 4455667",
    contactEmail: "contact@greenfuture.org",
    website: "https://greenfuture.foundation",
    verificationStatus: "VERIFIED",
    totalEventsCount: 14,
    registeredDate: "2024-01-15"
  },
  {
    organizationId: "org_202",
    name: "Indus Health Network Volunteers",
    description: "Mobilizing clinical and non-clinical volunteers to assist pediatric patient care, health awareness camps, and outpatient support.",
    location: "Korangi, Karachi",
    city: "Karachi",
    contactPhone: "+92 300 1122334",
    contactEmail: "volunteer@indushealth.org",
    website: "https://indushospital.org.pk",
    verificationStatus: "VERIFIED",
    totalEventsCount: 8,
    registeredDate: "2024-03-10"
  },
  {
    organizationId: "org_203",
    name: "Al-Khidmat Foundation Disaster Wing",
    description: "Leading humanitarian relief provider delivering hot meals, clean drinking water, and emergency rescue assistance during national emergencies.",
    location: "Gulshan-e-Iqbal, Karachi",
    city: "Karachi",
    contactPhone: "+92 333 9988776",
    contactEmail: "relief@alkhidmat.org",
    website: "https://alkhidmat.org",
    verificationStatus: "VERIFIED",
    totalEventsCount: 22,
    registeredDate: "2023-11-20"
  },
  {
    organizationId: "org_204",
    name: "Hope Street Welfare Trust",
    description: "Grassroots community initiative providing educational coaching and nutritional food packs for working children.",
    location: "Model Town, Lahore",
    city: "Lahore",
    contactPhone: "+92 345 5566778",
    contactEmail: "info@hopestreet.org",
    website: "https://hopestreettrust.org",
    verificationStatus: "PENDING",
    totalEventsCount: 1,
    registeredDate: "2026-09-10"
  }
];

const DEFAULT_EVENTS = [
  {
    eventId: "evt_1",
    organizationId: "org_201",
    organizationName: "Green Future Foundation",
    title: "Clifton Beach Plastic Cleanup Drive",
    description: "Join hands with over 40 youth volunteers to remove harmful single-use plastic debris, microplastics, and fishing nets along Clifton beach.",
    category: "Environmental",
    city: "Karachi",
    locationName: "Clifton Beach Promenade",
    address: "Sea View Promenade, Clifton Block 4, Karachi",
    dateText: "Sep 20, 2026",
    startTime: "07:30 AM",
    endTime: "11:00 AM",
    capacity: 45,
    volunteersAppliedCount: 38,
    volunteersAcceptedCount: 32,
    status: "ACTIVE"
  },
  {
    eventId: "evt_2",
    organizationId: "org_202",
    organizationName: "Indus Health Network",
    title: "Pediatric Hospital Play & Storytelling Day",
    description: "Bring joy and emotional comfort to children recovering in the pediatric inpatient ward through guided story reading, art, and board games.",
    category: "Healthcare",
    city: "Karachi",
    locationName: "Indus Hospital Campus",
    address: "Plot 18, Korangi Crossing Road, Karachi",
    dateText: "Sep 22, 2026",
    startTime: "02:00 PM",
    endTime: "05:00 PM",
    capacity: 15,
    volunteersAppliedCount: 15,
    volunteersAcceptedCount: 12,
    status: "ACTIVE"
  },
  {
    eventId: "evt_3",
    organizationId: "org_203",
    organizationName: "Al-Khidmat Foundation",
    title: "Flood Relief Supply Packing & Logistics",
    description: "Emergency relief packaging camp. Packing 2,000 emergency ration bags with flour, lentils, cooking oil, hydration salts, and medicines.",
    category: "Disaster Relief",
    city: "Karachi",
    locationName: "Al-Khidmat Central Logistics Hub",
    address: "Plot 42, Block 13-A, Gulshan-e-Iqbal, Karachi",
    dateText: "Sep 25, 2026",
    startTime: "09:00 AM",
    endTime: "04:00 PM",
    capacity: 60,
    volunteersAppliedCount: 60,
    volunteersAcceptedCount: 60,
    status: "FULL"
  },
  {
    eventId: "evt_4",
    organizationId: "org_204",
    organizationName: "Hope Street Welfare Trust",
    title: "Street Children Evening Literacy Tutoring",
    description: "Volunteer math and English mentors needed for primary students aged 8-14 to help with homework support and foundational reading.",
    category: "Education",
    city: "Lahore",
    locationName: "Hope Community Center",
    address: "14-L, Model Town Extension, Lahore",
    dateText: "Sep 28, 2026",
    startTime: "04:00 PM",
    endTime: "07:00 PM",
    capacity: 20,
    volunteersAppliedCount: 8,
    volunteersAcceptedCount: 6,
    status: "ACTIVE"
  },
  {
    eventId: "evt_5",
    organizationId: "org_201",
    organizationName: "Green Future Foundation",
    title: "Margalla Hills Reforestation & Seed Planting",
    description: "Community afforestation program planting 1,500 indigenous pine and olive saplings along designated nature trails in Margalla National Park.",
    category: "Environmental",
    city: "Islamabad",
    locationName: "Trail 5 Base Camp",
    address: "Margalla Road, Sector F-5, Islamabad",
    dateText: "Oct 02, 2026",
    startTime: "08:00 AM",
    endTime: "01:00 PM",
    capacity: 50,
    volunteersAppliedCount: 42,
    volunteersAcceptedCount: 35,
    status: "ACTIVE"
  },
  {
    eventId: "evt_6",
    organizationId: "org_202",
    organizationName: "Indus Health Network",
    title: "Free Community Blood Pressure & Diabetes Screening",
    description: "Public health screening camp in under-resourced suburban communities. Administering vitals, glucose testing, and nutrition counselling.",
    category: "Healthcare",
    city: "Rawalpindi",
    locationName: "Liaquat Community Hall",
    address: "Murree Road, Rawalpindi",
    dateText: "Aug 15, 2026",
    startTime: "09:00 AM",
    endTime: "03:00 PM",
    capacity: 25,
    volunteersAppliedCount: 25,
    volunteersAcceptedCount: 25,
    status: "COMPLETED"
  }
];

const DEFAULT_VOLUNTEERS = [
  {
    userId: "vol_101",
    fullName: "Ahmed Khan",
    phone: "+92 300 8214950",
    email: "ahmed.khan@connect.org",
    city: "Karachi",
    cnicMasked: "42101-*******-3",
    skills: ["First Aid", "Event Coordination", "Crowd Management"],
    interests: ["Environment", "Disaster Relief"],
    completedEventsCount: 6,
    hoursVolunteered: 38,
    activeApplicationsCount: 2,
    status: "VERIFIED"
  },
  {
    userId: "vol_102",
    fullName: "Fatima Zahra",
    phone: "+92 321 9876543",
    email: "fatima.zahra@gmail.com",
    city: "Lahore",
    cnicMasked: "35202-*******-4",
    skills: ["Tutoring", "Mathematics", "Child Psychology"],
    interests: ["Education", "Child Welfare"],
    completedEventsCount: 9,
    hoursVolunteered: 54,
    activeApplicationsCount: 1,
    status: "VERIFIED"
  },
  {
    userId: "vol_103",
    fullName: "Bilal Tariq",
    phone: "+92 333 4567890",
    email: "bilal.t@outlook.com",
    city: "Islamabad",
    cnicMasked: "61101-*******-7",
    skills: ["Logistics", "Heavy Lifting", "Emergency Driving"],
    interests: ["Disaster Relief", "Rescue"],
    completedEventsCount: 4,
    hoursVolunteered: 26,
    activeApplicationsCount: 1,
    status: "VERIFIED"
  },
  {
    userId: "vol_104",
    fullName: "Ayesha Siddiqui",
    phone: "+92 345 1234567",
    email: "ayesha.s@yahoo.com",
    city: "Rawalpindi",
    cnicMasked: "37405-*******-1",
    skills: ["Nursing", "Patient Vitals", "First Aid"],
    interests: ["Healthcare", "Elderly Care"],
    completedEventsCount: 7,
    hoursVolunteered: 42,
    activeApplicationsCount: 0,
    status: "VERIFIED"
  }
];

const DEFAULT_APPLICATIONS = [
  {
    applicationId: "app_501",
    eventId: "evt_1",
    eventTitle: "Clifton Beach Plastic Cleanup Drive",
    organizationId: "org_201",
    organizationName: "Green Future Foundation",
    volunteerId: "vol_101",
    volunteerName: "Ahmed Khan",
    volunteerAge: 21,
    appliedAtText: "Sep 10, 2026",
    status: "ACCEPTED",
    notes: "Experienced with previous coastal cleanups and first-aid."
  },
  {
    applicationId: "app_502",
    eventId: "evt_2",
    eventTitle: "Pediatric Hospital Play & Storytelling Day",
    organizationId: "org_202",
    organizationName: "Indus Health Network",
    volunteerId: "vol_101",
    volunteerName: "Ahmed Khan",
    volunteerAge: 21,
    appliedAtText: "Sep 11, 2026",
    status: "PENDING",
    notes: "Would love to help bring joy to pediatric kids."
  },
  {
    applicationId: "app_503",
    eventId: "evt_4",
    eventTitle: "Street Children Evening Literacy Tutoring",
    organizationId: "org_204",
    organizationName: "Hope Street Welfare Trust",
    volunteerId: "vol_102",
    volunteerName: "Fatima Zahra",
    volunteerAge: 23,
    appliedAtText: "Sep 11, 2026",
    status: "ACCEPTED",
    notes: "Certified high-school tutor."
  },
  {
    applicationId: "app_504",
    eventId: "evt_3",
    eventTitle: "Flood Relief Supply Packing & Logistics",
    organizationId: "org_203",
    organizationName: "Al-Khidmat Foundation",
    volunteerId: "vol_103",
    volunteerName: "Bilal Tariq",
    volunteerAge: 25,
    appliedAtText: "Sep 09, 2026",
    status: "ACCEPTED",
    notes: "Available for full Saturday packing shift."
  }
];

const DEFAULT_REPORTS = [
  {
    reportId: "rep_901",
    type: "ORGANIZATION_REVIEW",
    targetName: "Hope Street Welfare Trust",
    targetType: "Organization",
    reason: "New NGO registration awaiting verified trust deed and audit documents.",
    reportedBy: "Automated System Guard",
    date: "Sep 10, 2026",
    status: "OPEN"
  }
];

const DEFAULT_USERS = [
  {
    userId: "usr_admin_1",
    fullName: "Sumaira Fatima",
    email: "admin@volunteerconnect.org",
    password: "admin",
    role: "SUPER_ADMIN",
    phone: "+92 300 1234567",
    city: "Karachi",
    cnic: "42101-1234567-1",
    status: "ACTIVE",
    joinedDate: "Jan 10, 2024",
    skills: ["Platform Governance", "Disaster Response Coordination"]
  },
  {
    userId: "usr_org_1",
    fullName: "Tariq Mansoor",
    email: "coordinator@greenfuture.org",
    password: "org",
    role: "ORGANIZATION",
    organizationName: "Green Future Foundation",
    phone: "+92 321 4455667",
    city: "Karachi",
    cnic: "42201-9876543-3",
    status: "ACTIVE",
    joinedDate: "Feb 14, 2024",
    skills: ["Environmental Projects", "Field Logistics"]
  },
  {
    userId: "usr_org_2",
    fullName: "Dr. Farhan Ali",
    email: "volunteer@indushealth.org",
    password: "org",
    role: "ORGANIZATION",
    organizationName: "Indus Health Network Volunteers",
    phone: "+92 300 1122334",
    city: "Karachi",
    cnic: "42101-7788991-5",
    status: "ACTIVE",
    joinedDate: "Mar 10, 2024",
    skills: ["Healthcare Camps", "Pediatric Support"]
  },
  {
    userId: "usr_vol_1",
    fullName: "Ahmed Khan",
    email: "ahmed.khan@connect.org",
    password: "user",
    role: "VOLUNTEER",
    phone: "+92 300 8214950",
    city: "Karachi",
    cnic: "42101-5544332-3",
    status: "ACTIVE",
    joinedDate: "Mar 01, 2024",
    skills: ["First Aid", "Event Coordination", "Crowd Management"]
  },
  {
    userId: "usr_vol_2",
    fullName: "Fatima Zahra",
    email: "fatima.zahra@gmail.com",
    password: "user",
    role: "VOLUNTEER",
    phone: "+92 321 9876543",
    city: "Lahore",
    cnic: "35202-1122334-4",
    status: "ACTIVE",
    joinedDate: "Mar 15, 2024",
    skills: ["Tutoring", "Mathematics", "Child Welfare"]
  },
  {
    userId: "usr_vol_3",
    fullName: "Bilal Tariq",
    email: "bilal.t@outlook.com",
    password: "user",
    role: "VOLUNTEER",
    phone: "+92 333 4567890",
    city: "Islamabad",
    cnic: "61101-7788990-7",
    status: "ACTIVE",
    joinedDate: "Apr 10, 2024",
    skills: ["Logistics", "Heavy Lifting", "Emergency Driving"]
  },
  {
    userId: "usr_vol_4",
    fullName: "Ayesha Siddiqui",
    email: "ayesha.s@yahoo.com",
    password: "user",
    role: "VOLUNTEER",
    phone: "+92 345 1234567",
    city: "Rawalpindi",
    cnic: "37405-3344556-1",
    status: "SUSPENDED",
    joinedDate: "Apr 12, 2024",
    skills: ["Nursing", "Patient Vitals", "First Aid"]
  }
];

// App State Management with LocalStorage
class Store {
  constructor() {
    this.orgs = this.load("vc_orgs", DEFAULT_ORGS);
    this.events = this.load("vc_events", DEFAULT_EVENTS);
    this.volunteers = this.load("vc_volunteers", DEFAULT_VOLUNTEERS);
    this.applications = this.load("vc_applications", DEFAULT_APPLICATIONS);
    this.reports = this.load("vc_reports", DEFAULT_REPORTS);
    this.users = this.load("vc_users", DEFAULT_USERS);
    this.session = this.load("vc_current_session", {
      userId: "usr_admin_1",
      fullName: "Sumaira Fatima",
      email: "admin@volunteerconnect.org",
      role: "SUPER_ADMIN",
      city: "Karachi"
    });
  }

  load(key, fallback) {
    try {
      const data = localStorage.getItem(key);
      return data ? JSON.parse(data) : fallback;
    } catch (e) {
      return fallback;
    }
  }

  save() {
    try {
      localStorage.setItem("vc_orgs", JSON.stringify(this.orgs));
      localStorage.setItem("vc_events", JSON.stringify(this.events));
      localStorage.setItem("vc_volunteers", JSON.stringify(this.volunteers));
      localStorage.setItem("vc_applications", JSON.stringify(this.applications));
      localStorage.setItem("vc_reports", JSON.stringify(this.reports));
      localStorage.setItem("vc_users", JSON.stringify(this.users));
      if (this.session) {
        localStorage.setItem("vc_current_session", JSON.stringify(this.session));
      } else {
        localStorage.removeItem("vc_current_session");
      }
    } catch (e) {
      console.warn("LocalStorage save error", e);
    }
  }
}

const store = new Store();

// UI State
let activeView = "overview";
let growthChartInstance = null;
let categoryChartInstance = null;

// Initialize on DOM Ready
document.addEventListener("DOMContentLoaded", () => {
  setupNavigation();
  checkAuthSession();
  renderAllViews();
  setupCharts();
  populateOrgDropdown();
});

// Navigation Setup
function setupNavigation() {
  const navItems = document.querySelectorAll(".nav-item");
  navItems.forEach(item => {
    item.addEventListener("click", () => {
      const view = item.getAttribute("data-view");
      if (view) switchView(view);
    });
  });
}

function switchView(viewName) {
  activeView = viewName;
  
  // Update sidebar active classes
  document.querySelectorAll(".nav-item").forEach(item => {
    item.classList.toggle("active", item.getAttribute("data-view") === viewName);
  });

  // Update panels
  document.querySelectorAll(".view-panel").forEach(panel => {
    panel.classList.toggle("active", panel.id === `view-${viewName}`);
  });

  // Update title
  const titles = {
    overview: "Dashboard Overview",
    events: "Volunteer Events & Items Management",
    users: "User Accounts Management",
    organizations: "Partner Organizations & NGOs",
    volunteers: "Volunteers User Directory",
    applications: "Applications & Safety Limit Oversight",
    reports: "Safety Moderation & Triage Queue",
    broadcast: "Platform Push Broadcast Announcements",
    settings: "Settings & Cloud Sync Configuration"
  };
  document.getElementById("page-title").textContent = titles[viewName] || "Admin Portal";

  // Re-render specific views if needed
  if (viewName === "overview") {
    updateOverviewStats();
  } else if (viewName === "users") {
    renderUsersTable();
  } else if (viewName === "events") {
    renderEventsTable();
  }
}

// Render All Views
function renderAllViews() {
  updateOverviewStats();
  renderPendingOrgsOverview();
  renderEventsTable();
  renderUsersTable();
  renderOrgsTable();
  renderVolunteersTable();
  renderAppsTable();
  renderReportsTable();
  updateBadges();
}

// Badges count updater
function updateBadges() {
  const pendingOrgs = store.orgs.filter(o => o.verificationStatus === "PENDING").length;
  const badgePendingOrgs = document.getElementById("badge-pending-orgs");
  if (badgePendingOrgs) {
    badgePendingOrgs.textContent = pendingOrgs;
    badgePendingOrgs.style.display = pendingOrgs > 0 ? "inline-block" : "none";
  }

  const activeEvents = store.events.filter(e => e.status === "ACTIVE" || e.status === "FULL").length;
  const badgeEvents = document.getElementById("badge-events-count");
  if (badgeEvents) badgeEvents.textContent = activeEvents;

  const badgeUsers = document.getElementById("badge-users-count");
  if (badgeUsers) badgeUsers.textContent = store.users.length;

  const openReports = store.reports.filter(r => r.status === "OPEN").length;
  const badgeReports = document.getElementById("badge-reports");
  if (badgeReports) {
    badgeReports.textContent = openReports;
    badgeReports.style.display = openReports > 0 ? "inline-block" : "none";
  }
}

// 1. OVERVIEW STATS & CHARTS
function updateOverviewStats() {
  const activeEvents = store.events.filter(e => e.status === "ACTIVE" || e.status === "FULL").length;
  const verifiedOrgs = store.orgs.filter(o => o.verificationStatus === "VERIFIED").length;
  const pendingOrgs = store.orgs.filter(o => o.verificationStatus === "PENDING").length;
  const totalApps = store.applications.length;
  const openReports = store.reports.filter(r => r.status === "OPEN").length;
  
  const totalHours = store.volunteers.reduce((sum, v) => sum + (v.hoursVolunteered || 0), 0) + 3680;

  document.getElementById("stat-active-events").textContent = activeEvents;
  document.getElementById("stat-verified-orgs").textContent = verifiedOrgs;
  document.getElementById("stat-total-applications").textContent = totalApps;
  document.getElementById("stat-open-reports").textContent = openReports;
  document.getElementById("stat-total-hours").textContent = totalHours.toLocaleString();
  
  const pendingSub = document.getElementById("stat-pending-org-sub");
  if (pendingSub) {
    pendingSub.textContent = `${pendingOrgs} pending verification`;
  }
}

function renderPendingOrgsOverview() {
  const tbody = document.getElementById("overview-pending-orgs-body");
  if (!tbody) return;

  const pending = store.orgs.filter(o => o.verificationStatus === "PENDING");
  if (pending.length === 0) {
    tbody.innerHTML = `<tr><td colspan="5" style="text-align: center; color: var(--text-muted); padding: 24px;">All organizations verified. No pending approvals in queue.</td></tr>`;
    return;
  }

  tbody.innerHTML = pending.map(org => `
    <tr>
      <td>
        <strong style="color: var(--navy-dark);">${escapeHtml(org.name)}</strong>
        <div style="font-size: 11px; color: var(--text-muted);">${escapeHtml(org.organizationId)}</div>
      </td>
      <td>
        <div>${escapeHtml(org.location)}</div>
        <div style="font-size: 11px; color: var(--text-muted);">${escapeHtml(org.contactEmail)}</div>
      </td>
      <td><span style="font-size: 12px; color: var(--text-muted);">${escapeHtml(org.description.substring(0, 60))}...</span></td>
      <td><span class="badge badge-warning">Pending Review</span></td>
      <td>
        <button class="btn btn-primary btn-sm" onclick="approveOrg('${org.organizationId}')">
          <i class="fa-solid fa-check"></i> Approve
        </button>
        <button class="btn btn-outline-danger btn-sm" onclick="rejectOrg('${org.organizationId}')">
          Reject
        </button>
      </td>
    </tr>
  `).join("");
}

function setupCharts() {
  const growthCtx = document.getElementById("growthChart");
  if (growthCtx) {
    growthChartInstance = new Chart(growthCtx, {
      type: 'line',
      data: {
        labels: ['Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep'],
        datasets: [
          {
            label: 'Volunteer Signups',
            data: [210, 340, 490, 680, 950, 1420],
            borderColor: '#184DD1',
            backgroundColor: 'rgba(24, 77, 209, 0.1)',
            tension: 0.35,
            fill: true
          },
          {
            label: 'Community Hours Logged',
            data: [420, 780, 1200, 1850, 2600, 3840],
            borderColor: '#10B981',
            backgroundColor: 'transparent',
            tension: 0.35,
            borderDash: [5, 5]
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: { position: 'top', labels: { boxWidth: 12, font: { family: 'Plus Jakarta Sans', size: 12 } } }
        },
        scales: {
          y: { grid: { color: '#F1F5F9' }, ticks: { font: { family: 'Plus Jakarta Sans', size: 11 } } },
          x: { grid: { display: false }, ticks: { font: { family: 'Plus Jakarta Sans', size: 11 } } }
        }
      }
    });
  }

  const catCtx = document.getElementById("categoryChart");
  if (catCtx) {
    categoryChartInstance = new Chart(catCtx, {
      type: 'doughnut',
      data: {
        labels: ['Environmental', 'Healthcare', 'Disaster Relief', 'Education', 'Animal Welfare'],
        datasets: [{
          data: [35, 25, 20, 15, 5],
          backgroundColor: ['#184DD1', '#10B981', '#F59E0B', '#9333EA', '#0284C7'],
          borderWidth: 2,
          borderColor: '#FFFFFF'
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: { position: 'bottom', labels: { boxWidth: 10, font: { family: 'Plus Jakarta Sans', size: 11 } } }
        },
        cutout: '65%'
      }
    });
  }
}

// 2. EVENTS MANAGEMENT
function renderEventsTable() {
  const tbody = document.getElementById("events-table-body");
  if (!tbody) return;

  const search = (document.getElementById("event-search-input")?.value || "").toLowerCase();
  const catFilter = document.getElementById("event-category-filter")?.value || "ALL";
  const statusFilter = document.getElementById("event-status-filter")?.value || "ALL";

  const filtered = store.events.filter(e => {
    const matchSearch = e.title.toLowerCase().includes(search) || e.address.toLowerCase().includes(search) || e.organizationName.toLowerCase().includes(search);
    const matchCat = catFilter === "ALL" || e.category === catFilter;
    const matchStatus = statusFilter === "ALL" || e.status === statusFilter;
    return matchSearch && matchCat && matchStatus;
  });

  if (filtered.length === 0) {
    tbody.innerHTML = `<tr><td colspan="7" style="text-align: center; color: var(--text-muted); padding: 32px;">No matching volunteer events found.</td></tr>`;
    return;
  }

  tbody.innerHTML = filtered.map(evt => {
    const fillPercent = Math.min(100, Math.round((evt.volunteersAcceptedCount / evt.capacity) * 100));
    const isFull = fillPercent >= 100;
    
    let badgeClass = "badge-success";
    if (evt.status === "FULL" || isFull) badgeClass = "badge-warning";
    if (evt.status === "COMPLETED") badgeClass = "badge-neutral";
    if (evt.status === "CANCELLED") badgeClass = "badge-danger";

    return `
      <tr>
        <td>
          <div style="font-weight: 700; color: var(--navy-dark); font-size: 13.5px;">${escapeHtml(evt.title)}</div>
          <div style="font-size: 11px; color: var(--text-muted);">${escapeHtml(evt.address)}</div>
        </td>
        <td>
          <span style="font-weight: 500;">${escapeHtml(evt.organizationName)}</span>
        </td>
        <td>
          <span class="badge badge-primary">${escapeHtml(evt.category)}</span>
        </td>
        <td>
          <div>${escapeHtml(evt.dateText)}</div>
          <div style="font-size: 11px; color: var(--text-muted);">${escapeHtml(evt.startTime)} (${escapeHtml(evt.city)})</div>
        </td>
        <td>
          <div style="font-size: 12px; font-weight: 600;">${evt.volunteersAcceptedCount} / ${evt.capacity} <span style="font-weight: normal; color: var(--text-muted);">(${fillPercent}%)</span></div>
          <div class="capacity-bar-container">
            <div class="capacity-bar ${isFull ? 'full' : ''}" style="width: ${fillPercent}%;"></div>
          </div>
        </td>
        <td>
          <span class="badge ${badgeClass}">${evt.status}</span>
        </td>
        <td>
          <div style="display: flex; gap: 6px;">
            <button class="btn btn-secondary btn-sm" onclick="viewEventDetails('${evt.eventId}')" title="View details">
              <i class="fa-solid fa-eye"></i>
            </button>
            <button class="btn btn-secondary btn-sm" onclick="openEditEventModal('${evt.eventId}')" title="Edit event / item">
              <i class="fa-solid fa-pen-to-square"></i>
            </button>
            <button class="btn btn-secondary btn-sm" onclick="toggleEventStatus('${evt.eventId}')" title="Toggle Active/Completed">
              <i class="fa-solid fa-rotate"></i>
            </button>
            <button class="btn btn-outline-danger btn-sm" onclick="deleteEvent('${evt.eventId}')" title="Delete event">
              <i class="fa-solid fa-trash"></i>
            </button>
          </div>
        </td>
      </tr>
    `;
  }).join("");
}

function filterEventsTable() {
  renderEventsTable();
}

function viewEventDetails(eventId) {
  const evt = store.events.find(e => e.eventId === eventId);
  if (!evt) return;

  const content = `
    <div style="display: flex; flex-direction: column; gap: 14px;">
      <div>
        <span class="badge badge-primary">${escapeHtml(evt.category)}</span>
        <span class="badge badge-success" style="margin-left: 6px;">${escapeHtml(evt.status)}</span>
        <h4 style="font-size: 18px; color: var(--navy-dark); margin-top: 8px;">${escapeHtml(evt.title)}</h4>
        <p style="font-size: 13px; color: var(--text-muted);">${escapeHtml(evt.organizationName)} • ${escapeHtml(evt.city)}</p>
      </div>

      <div style="background: #F8FAFC; padding: 14px; border-radius: 8px; border: 1px solid var(--border);">
        <p style="font-size: 13px; line-height: 1.6;">${escapeHtml(evt.description)}</p>
      </div>

      <div class="form-row">
        <div>
          <label style="font-size: 11px; font-weight: 700; color: var(--text-muted); text-transform: uppercase;">Date & Time</label>
          <div style="font-size: 13px; font-weight: 600;">${escapeHtml(evt.dateText)} | ${escapeHtml(evt.startTime)} - ${escapeHtml(evt.endTime)}</div>
        </div>
        <div>
          <label style="font-size: 11px; font-weight: 700; color: var(--text-muted); text-transform: uppercase;">Venue Location</label>
          <div style="font-size: 13px; font-weight: 600;">${escapeHtml(evt.address)}</div>
        </div>
      </div>

      <div class="form-row">
        <div>
          <label style="font-size: 11px; font-weight: 700; color: var(--text-muted); text-transform: uppercase;">Capacity Fill</label>
          <div style="font-size: 13px; font-weight: 600;">${evt.volunteersAcceptedCount} accepted of ${evt.capacity} capacity (${evt.volunteersAppliedCount} applied)</div>
        </div>
        <div>
          <label style="font-size: 11px; font-weight: 700; color: var(--text-muted); text-transform: uppercase;">Event ID</label>
          <div style="font-size: 13px; font-family: monospace; color: var(--text-muted);">${evt.eventId}</div>
        </div>
      </div>
    </div>
  `;

  document.getElementById("modal-details-title").textContent = "Volunteer Event Inspection";
  document.getElementById("modal-details-content").innerHTML = content;
  openModal("modal-details");
}

function toggleEventStatus(eventId) {
  const evt = store.events.find(e => e.eventId === eventId);
  if (!evt) return;

  if (evt.status === "ACTIVE") evt.status = "COMPLETED";
  else if (evt.status === "COMPLETED") evt.status = "ACTIVE";
  else if (evt.status === "FULL") evt.status = "ACTIVE";

  store.save();
  renderEventsTable();
  updateOverviewStats();
  showToast(`Updated status for "${evt.title}" to ${evt.status}`, "success");
}

function deleteEvent(eventId) {
  if (!confirm("Are you sure you want to permanently delete this event? This will also cancel all volunteer registrations.")) return;

  store.events = store.events.filter(e => e.eventId !== eventId);
  store.applications = store.applications.filter(a => a.eventId !== eventId);
  store.save();

  renderEventsTable();
  renderAppsTable();
  updateOverviewStats();
  updateBadges();
  showToast("Event successfully deleted", "danger");
}

// 3. ORGANIZATIONS MANAGEMENT
function renderOrgsTable() {
  const tbody = document.getElementById("orgs-table-body");
  if (!tbody) return;

  const search = (document.getElementById("org-search-input")?.value || "").toLowerCase();
  const statusFilter = document.getElementById("org-status-filter")?.value || "ALL";

  const filtered = store.orgs.filter(o => {
    const matchSearch = o.name.toLowerCase().includes(search) || o.contactEmail.toLowerCase().includes(search) || o.city.toLowerCase().includes(search);
    const matchStatus = statusFilter === "ALL" || o.verificationStatus === statusFilter;
    return matchSearch && matchStatus;
  });

  if (filtered.length === 0) {
    tbody.innerHTML = `<tr><td colspan="7" style="text-align: center; color: var(--text-muted); padding: 32px;">No organizations found.</td></tr>`;
    return;
  }

  tbody.innerHTML = filtered.map(org => {
    let badge = `<span class="badge badge-success"><i class="fa-solid fa-circle-check"></i> Verified</span>`;
    if (org.verificationStatus === "PENDING") {
      badge = `<span class="badge badge-warning"><i class="fa-solid fa-clock"></i> Pending Review</span>`;
    } else if (org.verificationStatus === "REJECTED") {
      badge = `<span class="badge badge-danger"><i class="fa-solid fa-ban"></i> Rejected</span>`;
    }

    return `
      <tr>
        <td>
          <div style="font-weight: 700; color: var(--navy-dark);">${escapeHtml(org.name)}</div>
          <div style="font-size: 11px; color: var(--text-muted);">${escapeHtml(org.organizationId)}</div>
        </td>
        <td>
          <div>${escapeHtml(org.location)}</div>
        </td>
        <td>
          <div>${escapeHtml(org.contactEmail)}</div>
          <div style="font-size: 11px; color: var(--text-muted);">${escapeHtml(org.contactPhone)}</div>
        </td>
        <td>
          <a href="${escapeHtml(org.website)}" target="_blank" style="color: var(--primary); font-size: 12px; text-decoration: none;">
            <i class="fa-solid fa-arrow-up-right-from-square"></i> Visit Website
          </a>
        </td>
        <td>
          <span style="font-weight: 600;">${org.totalEventsCount}</span> events
        </td>
        <td>${badge}</td>
        <td>
          ${org.verificationStatus === "PENDING" ? `
            <button class="btn btn-primary btn-sm" onclick="approveOrg('${org.organizationId}')">
              <i class="fa-solid fa-check"></i> Approve
            </button>
            <button class="btn btn-outline-danger btn-sm" onclick="rejectOrg('${org.organizationId}')">
              Reject
            </button>
          ` : `
            <button class="btn btn-secondary btn-sm" onclick="viewOrgDetails('${org.organizationId}')">
              <i class="fa-solid fa-circle-info"></i> Info
            </button>
          `}
        </td>
      </tr>
    `;
  }).join("");
}

function filterOrgsTable() {
  renderOrgsTable();
}

function approveOrg(orgId) {
  const org = store.orgs.find(o => o.organizationId === orgId);
  if (!org) return;

  org.verificationStatus = "VERIFIED";
  store.reports = store.reports.filter(r => r.targetName !== org.name);
  store.save();

  renderOrgsTable();
  renderPendingOrgsOverview();
  renderReportsTable();
  updateOverviewStats();
  updateBadges();

  showToast(`Organization "${org.name}" has been officially verified!`, "success");
}

function rejectOrg(orgId) {
  const org = store.orgs.find(o => o.organizationId === orgId);
  if (!org) return;

  org.verificationStatus = "REJECTED";
  store.save();

  renderOrgsTable();
  renderPendingOrgsOverview();
  updateBadges();

  showToast(`Organization "${org.name}" registration was rejected.`, "warning");
}

function viewOrgDetails(orgId) {
  const org = store.orgs.find(o => o.organizationId === orgId);
  if (!org) return;

  const content = `
    <div style="display: flex; flex-direction: column; gap: 14px;">
      <div>
        <h4 style="font-size: 18px; color: var(--navy-dark);">${escapeHtml(org.name)}</h4>
        <p style="font-size: 12px; color: var(--text-muted);">${escapeHtml(org.location)} • Registered ${escapeHtml(org.registeredDate || '2024')}</p>
      </div>
      <div style="background: #F8FAFC; padding: 14px; border-radius: 8px; border: 1px solid var(--border);">
        <p style="font-size: 13px; line-height: 1.5;">${escapeHtml(org.description)}</p>
      </div>
      <div class="form-row">
        <div>
          <label style="font-size: 11px; font-weight: 700; color: var(--text-muted); text-transform: uppercase;">Official Contact</label>
          <div style="font-size: 13px;">${escapeHtml(org.contactEmail)}<br>${escapeHtml(org.contactPhone)}</div>
        </div>
        <div>
          <label style="font-size: 11px; font-weight: 700; color: var(--text-muted); text-transform: uppercase;">Portal Status</label>
          <div style="font-size: 13px; font-weight: 600; color: var(--success);">${escapeHtml(org.verificationStatus)}</div>
        </div>
      </div>
    </div>
  `;

  document.getElementById("modal-details-title").textContent = "Organization Profile";
  document.getElementById("modal-details-content").innerHTML = content;
  openModal("modal-details");
}

// 4. VOLUNTEERS DIRECTORY
function renderVolunteersTable() {
  const tbody = document.getElementById("volunteers-table-body");
  if (!tbody) return;

  const search = (document.getElementById("volunteer-search-input")?.value || "").toLowerCase();
  const cityFilter = document.getElementById("volunteer-city-filter")?.value || "ALL";

  const filtered = store.volunteers.filter(v => {
    const matchSearch = v.fullName.toLowerCase().includes(search) || v.city.toLowerCase().includes(search) || v.email.toLowerCase().includes(search);
    const matchCity = cityFilter === "ALL" || v.city === cityFilter;
    return matchSearch && matchCity;
  });

  if (filtered.length === 0) {
    tbody.innerHTML = `<tr><td colspan="7" style="text-align: center; color: var(--text-muted); padding: 32px;">No volunteers found.</td></tr>`;
    return;
  }

  tbody.innerHTML = filtered.map(vol => `
    <tr>
      <td>
        <div style="font-weight: 700; color: var(--navy-dark);">${escapeHtml(vol.fullName)}</div>
        <div style="font-size: 11px; color: var(--text-muted);">${escapeHtml(vol.email)}</div>
      </td>
      <td>
        <div>${escapeHtml(vol.city)}</div>
        <div style="font-size: 11px; color: var(--text-muted);">${escapeHtml(vol.phone)}</div>
      </td>
      <td>
        <span style="font-family: monospace; font-size: 12px; background: #F1F5F9; padding: 2px 6px; border-radius: 4px;">
          ${escapeHtml(vol.cnicMasked)}
        </span>
      </td>
      <td>
        <div style="display: flex; gap: 4px; flex-wrap: wrap;">
          ${(vol.skills || []).map(s => `<span class="badge badge-primary" style="font-size: 10px;">${escapeHtml(s)}</span>`).join("")}
        </div>
      </td>
      <td>
        <strong style="color: var(--navy-dark);">${vol.completedEventsCount || 0}</strong> events
      </td>
      <td>
        <span style="color: var(--success); font-weight: 700;">${vol.hoursVolunteered || 0} hrs</span>
      </td>
      <td>
        <button class="btn btn-secondary btn-sm" onclick="viewVolunteerDetails('${vol.userId}')">
          <i class="fa-solid fa-id-card"></i> Profile
        </button>
      </td>
    </tr>
  `).join("");
}

function filterVolunteersTable() {
  renderVolunteersTable();
}

function viewVolunteerDetails(userId) {
  const vol = store.volunteers.find(v => v.userId === userId);
  if (!vol) return;

  const content = `
    <div style="display: flex; flex-direction: column; gap: 14px;">
      <div style="display: flex; align-items: center; gap: 14px;">
        <div style="width: 50px; height: 50px; border-radius: 50%; background: var(--primary); color: #fff; display: flex; align-items: center; justify-content: center; font-size: 20px; font-weight: bold;">
          ${vol.fullName.charAt(0)}
        </div>
        <div>
          <h4 style="font-size: 18px; color: var(--navy-dark);">${escapeHtml(vol.fullName)}</h4>
          <p style="font-size: 12px; color: var(--text-muted);">${escapeHtml(vol.city)} • Verified Volunteer Member</p>
        </div>
      </div>

      <div class="form-row">
        <div>
          <label style="font-size: 11px; font-weight: 700; color: var(--text-muted); text-transform: uppercase;">Contact & Identity</label>
          <div style="font-size: 13px;">${escapeHtml(vol.email)}<br>${escapeHtml(vol.phone)}<br>CNIC: <strong>${escapeHtml(vol.cnicMasked)}</strong></div>
        </div>
        <div>
          <label style="font-size: 11px; font-weight: 700; color: var(--text-muted); text-transform: uppercase;">Community Impact</label>
          <div style="font-size: 13px;"><strong>${vol.completedEventsCount}</strong> events completed<br><strong style="color: var(--success);">${vol.hoursVolunteered}</strong> total verified hours</div>
        </div>
      </div>

      <div>
        <label style="font-size: 11px; font-weight: 700; color: var(--text-muted); text-transform: uppercase;">Skills</label>
        <div style="display: flex; gap: 6px; flex-wrap: wrap; margin-top: 4px;">
          ${(vol.skills || []).map(s => `<span class="badge badge-primary">${escapeHtml(s)}</span>`).join("")}
        </div>
      </div>
    </div>
  `;

  document.getElementById("modal-details-title").textContent = "Volunteer Profile";
  document.getElementById("modal-details-content").innerHTML = content;
  openModal("modal-details");
}

// 5. APPLICATIONS OVERSIGHT
function renderAppsTable() {
  const tbody = document.getElementById("apps-table-body");
  if (!tbody) return;

  const search = (document.getElementById("app-search-input")?.value || "").toLowerCase();
  const statusFilter = document.getElementById("app-status-filter")?.value || "ALL";

  const filtered = store.applications.filter(a => {
    const matchSearch = a.volunteerName.toLowerCase().includes(search) || a.eventTitle.toLowerCase().includes(search) || a.organizationName.toLowerCase().includes(search);
    const matchStatus = statusFilter === "ALL" || a.status === statusFilter;
    return matchSearch && matchStatus;
  });

  if (filtered.length === 0) {
    tbody.innerHTML = `<tr><td colspan="7" style="text-align: center; color: var(--text-muted); padding: 32px;">No applications found.</td></tr>`;
    return;
  }

  tbody.innerHTML = filtered.map(app => {
    let badgeClass = "badge-warning";
    if (app.status === "ACCEPTED") badgeClass = "badge-success";
    if (app.status === "REJECTED") badgeClass = "badge-danger";
    if (app.status === "CANCELLED") badgeClass = "badge-neutral";

    return `
      <tr>
        <td>
          <div style="font-weight: 700; color: var(--navy-dark);">${escapeHtml(app.volunteerName)}</div>
          <div style="font-size: 11px; color: var(--text-muted);">Age ${app.volunteerAge || 20}</div>
        </td>
        <td>
          <div style="font-weight: 600;">${escapeHtml(app.eventTitle)}</div>
        </td>
        <td>
          <span>${escapeHtml(app.organizationName)}</span>
        </td>
        <td>
          <span style="font-size: 12px;">${escapeHtml(app.appliedAtText)}</span>
        </td>
        <td>
          <span class="badge badge-info" style="font-size: 10px;">Enforced ≤ 2 Active</span>
        </td>
        <td>
          <span class="badge ${badgeClass}">${app.status}</span>
        </td>
        <td>
          <div style="display: flex; gap: 6px;">
            ${app.status === "PENDING" ? `
              <button class="btn btn-primary btn-sm" onclick="setAppStatus('${app.applicationId}', 'ACCEPTED')">
                Accept
              </button>
              <button class="btn btn-outline-danger btn-sm" onclick="setAppStatus('${app.applicationId}', 'REJECTED')">
                Reject
              </button>
            ` : `
              <button class="btn btn-secondary btn-sm" onclick="setAppStatus('${app.applicationId}', 'PENDING')">
                Reset
              </button>
            `}
          </div>
        </td>
      </tr>
    `;
  }).join("");
}

function filterAppsTable() {
  renderAppsTable();
}

function setAppStatus(appId, newStatus) {
  const app = store.applications.find(a => a.applicationId === appId);
  if (!app) return;

  app.status = newStatus;
  store.save();

  renderAppsTable();
  updateOverviewStats();
  showToast(`Application for ${app.volunteerName} marked as ${newStatus}`, "success");
}

// 6. REPORTS & MODERATION
function renderReportsTable() {
  const tbody = document.getElementById("reports-table-body");
  if (!tbody) return;

  const statusFilter = document.getElementById("report-status-filter")?.value || "ALL";

  const filtered = store.reports.filter(r => {
    return statusFilter === "ALL" || r.status === statusFilter;
  });

  if (filtered.length === 0) {
    tbody.innerHTML = `<tr><td colspan="7" style="text-align: center; color: var(--text-muted); padding: 32px;">No active reports in triage queue. Community safety is green!</td></tr>`;
    return;
  }

  tbody.innerHTML = filtered.map(rep => `
    <tr>
      <td>
        <strong style="color: var(--navy-dark);">${escapeHtml(rep.reportId)}</strong>
        <div style="font-size: 11px; color: var(--text-muted);">${escapeHtml(rep.type)}</div>
      </td>
      <td>
        <strong>${escapeHtml(rep.targetName)}</strong>
        <div style="font-size: 11px; color: var(--text-muted);">${escapeHtml(rep.targetType)}</div>
      </td>
      <td>
        <span style="font-size: 12.5px;">${escapeHtml(rep.reason)}</span>
      </td>
      <td>
        <span style="font-size: 12px; color: var(--text-muted);">${escapeHtml(rep.reportedBy)}</span>
      </td>
      <td>
        <span style="font-size: 12px;">${escapeHtml(rep.date)}</span>
      </td>
      <td>
        <span class="badge ${rep.status === 'OPEN' ? 'badge-danger' : 'badge-success'}">${rep.status}</span>
      </td>
      <td>
        ${rep.status === 'OPEN' ? `
          <button class="btn btn-primary btn-sm" onclick="resolveReport('${rep.reportId}', 'RESOLVED')">
            <i class="fa-solid fa-check"></i> Resolve
          </button>
          <button class="btn btn-secondary btn-sm" onclick="resolveReport('${rep.reportId}', 'DISMISSED')">
            Dismiss
          </button>
        ` : `
          <span style="color: var(--text-light); font-size: 12px;">Triage Completed</span>
        `}
      </td>
    </tr>
  `).join("");
}

function filterReportsTable() {
  renderReportsTable();
}

function resolveReport(reportId, outcome) {
  const rep = store.reports.find(r => r.reportId === reportId);
  if (!rep) return;

  rep.status = outcome;
  store.save();

  renderReportsTable();
  updateOverviewStats();
  updateBadges();

  showToast(`Report ${reportId} marked as ${outcome}`, "info");
}

// 7. BROADCAST ALERT HANDLER
function handleSendBroadcast(event) {
  event.preventDefault();
  const audience = document.getElementById("broadcast-audience").value;
  const type = document.getElementById("broadcast-type").value;
  const title = document.getElementById("broadcast-title").value;
  const message = document.getElementById("broadcast-message").value;

  showToast(`Broadcast sent to ${audience}: "${title}"`, "success");
  event.target.reset();
}

// 8. EVENT CREATION / EDITING
function populateOrgDropdown() {
  const select = document.getElementById("event-form-org");
  if (!select) return;

  select.innerHTML = store.orgs.map(o => `
    <option value="${o.organizationId}">${escapeHtml(o.name)}</option>
  `).join("");
}

function openCreateEventModal() {
  document.getElementById("modal-event-title").textContent = "Create Volunteer Event / Item";
  document.getElementById("event-form-id").value = "";
  document.getElementById("event-form-title").value = "";
  document.getElementById("event-form-address").value = "";
  document.getElementById("event-form-desc").value = "";
  document.getElementById("event-form-status").value = "ACTIVE";
  document.getElementById("event-form-capacity").value = 40;
  document.getElementById("btn-save-event").textContent = "Publish Event / Item";
  populateOrgDropdown();
  openModal("modal-event");
}

function openEditEventModal(eventId) {
  const evt = store.events.find(e => e.eventId === eventId);
  if (!evt) return;

  populateOrgDropdown();
  document.getElementById("modal-event-title").textContent = "Edit Volunteer Event / Item";
  document.getElementById("event-form-id").value = evt.eventId;
  document.getElementById("event-form-title").value = evt.title;
  document.getElementById("event-form-category").value = evt.category;
  document.getElementById("event-form-org").value = evt.organizationId;
  document.getElementById("event-form-city").value = evt.city;
  document.getElementById("event-form-capacity").value = evt.capacity;
  document.getElementById("event-form-date").value = evt.dateText;
  document.getElementById("event-form-time").value = `${evt.startTime} - ${evt.endTime}`;
  document.getElementById("event-form-address").value = evt.address || evt.locationName;
  document.getElementById("event-form-status").value = evt.status || "ACTIVE";
  document.getElementById("event-form-desc").value = evt.description;
  document.getElementById("btn-save-event").textContent = "Update Event / Item";

  openModal("modal-event");
}

function handleSaveEvent(event) {
  event.preventDefault();

  const eventId = document.getElementById("event-form-id").value;
  const isEdit = !!eventId;
  const title = document.getElementById("event-form-title").value.trim();
  const category = document.getElementById("event-form-category").value;
  const orgId = document.getElementById("event-form-org").value;
  const city = document.getElementById("event-form-city").value;
  const capacity = parseInt(document.getElementById("event-form-capacity").value, 10) || 30;
  const dateText = document.getElementById("event-form-date").value.trim();
  const timeText = document.getElementById("event-form-time").value.trim();
  const address = document.getElementById("event-form-address").value.trim();
  const status = document.getElementById("event-form-status")?.value || "ACTIVE";
  const desc = document.getElementById("event-form-desc").value.trim();

  const org = store.orgs.find(o => o.organizationId === orgId);
  const orgName = org ? org.name : "Volunteer Connect Platform";

  if (isEdit) {
    const existing = store.events.find(e => e.eventId === eventId);
    if (existing) {
      existing.title = title;
      existing.category = category;
      existing.organizationId = orgId;
      existing.organizationName = orgName;
      existing.city = city;
      existing.capacity = capacity;
      existing.dateText = dateText;
      existing.startTime = timeText.split("-")[0]?.trim() || "09:00 AM";
      existing.endTime = timeText.split("-")[1]?.trim() || "01:00 PM";
      existing.address = address;
      existing.locationName = address;
      existing.status = status;
      existing.description = desc;
    }
    store.save();
    closeModal("modal-event");
    renderEventsTable();
    updateOverviewStats();
    updateBadges();
    showToast(`Event / Item "${title}" updated successfully!`, "success");
  } else {
    const newId = `evt_${Date.now()}`;
    const newEvent = {
      eventId: newId,
      organizationId: orgId,
      organizationName: orgName,
      title: title,
      description: desc,
      category: category,
      city: city,
      locationName: address,
      address: address,
      dateText: dateText,
      startTime: timeText.split("-")[0]?.trim() || "09:00 AM",
      endTime: timeText.split("-")[1]?.trim() || "01:00 PM",
      capacity: capacity,
      volunteersAppliedCount: 0,
      volunteersAcceptedCount: 0,
      status: status
    };
    store.events.unshift(newEvent);
    if (org) org.totalEventsCount = (org.totalEventsCount || 0) + 1;
    store.save();
    closeModal("modal-event");
    renderEventsTable();
    updateOverviewStats();
    updateBadges();
    showToast(`New Event / Item "${title}" published successfully!`, "success");
  }
}

// 8.5 USER ACCOUNTS MANAGEMENT (CRUD)
function renderUsersTable() {
  const tbody = document.getElementById("users-table-body");
  if (!tbody) return;

  const search = (document.getElementById("user-search-input")?.value || "").toLowerCase();
  const roleFilter = document.getElementById("user-role-filter")?.value || "ALL";
  const statusFilter = document.getElementById("user-status-filter")?.value || "ALL";

  const filtered = store.users.filter(u => {
    const matchSearch = (u.fullName || "").toLowerCase().includes(search) ||
                        (u.email || "").toLowerCase().includes(search) ||
                        (u.phone || "").toLowerCase().includes(search) ||
                        (u.cnic || "").toLowerCase().includes(search) ||
                        (u.city || "").toLowerCase().includes(search);
    const matchRole = roleFilter === "ALL" || u.role === roleFilter;
    const matchStatus = statusFilter === "ALL" || u.status === statusFilter;
    return matchSearch && matchRole && matchStatus;
  });

  if (filtered.length === 0) {
    tbody.innerHTML = `<tr><td colspan="7" style="text-align: center; color: var(--text-muted); padding: 32px;">No matching user accounts found.</td></tr>`;
    return;
  }

  tbody.innerHTML = filtered.map(u => {
    let roleBadge = `<span class="badge badge-role-vol"><i class="fa-solid fa-user"></i> Volunteer</span>`;
    if (u.role === "SUPER_ADMIN") {
      roleBadge = `<span class="badge badge-role-admin"><i class="fa-solid fa-crown"></i> Super Admin</span>`;
    } else if (u.role === "ORGANIZATION") {
      roleBadge = `<span class="badge badge-role-org"><i class="fa-solid fa-building"></i> Organization</span>`;
    }

    let statusBadge = `<span class="badge badge-success">Active</span>`;
    if (u.status === "SUSPENDED") {
      statusBadge = `<span class="badge badge-danger">Suspended</span>`;
    } else if (u.status === "PENDING") {
      statusBadge = `<span class="badge badge-warning">Pending</span>`;
    }

    const initial = (u.fullName || "U").charAt(0).toUpperCase();

    return `
      <tr>
        <td>
          <div style="display: flex; align-items: center; gap: 10px;">
            <div style="width: 36px; height: 36px; border-radius: 50%; background: linear-gradient(135deg, var(--primary), var(--navy-dark)); color: #fff; display: flex; align-items: center; justify-content: center; font-weight: 700; font-size: 13px; flex-shrink: 0;">
              ${initial}
            </div>
            <div>
              <div style="font-weight: 700; color: var(--navy-dark); font-size: 13.5px;">${escapeHtml(u.fullName)}</div>
              <div style="font-size: 11px; color: var(--text-muted);">${escapeHtml(u.email)}</div>
            </div>
          </div>
        </td>
        <td>${roleBadge}</td>
        <td>
          <div>${escapeHtml(u.city || "Karachi")}</div>
          <div style="font-size: 11px; color: var(--text-muted);">${escapeHtml(u.phone || "--")}</div>
        </td>
        <td>
          <span style="font-family: monospace; font-size: 12px; background: #F1F5F9; padding: 2px 6px; border-radius: 4px;">
            ${escapeHtml(u.cnic || "Protected")}
          </span>
        </td>
        <td>${statusBadge}</td>
        <td>
          <span style="font-size: 12px; color: var(--text-muted);">${escapeHtml(u.joinedDate || "Recent")}</span>
        </td>
        <td>
          <div style="display: flex; gap: 6px;">
            <button class="btn btn-secondary btn-sm" onclick="openEditUserModal('${u.userId}')" title="Edit user account">
              <i class="fa-solid fa-pen-to-square"></i>
            </button>
            <button class="btn btn-secondary btn-sm" onclick="toggleUserStatus('${u.userId}')" title="${u.status === 'ACTIVE' ? 'Suspend user' : 'Activate user'}">
              <i class="fa-solid ${u.status === 'ACTIVE' ? 'fa-user-slash' : 'fa-user-check'}"></i>
            </button>
            <button class="btn btn-outline-danger btn-sm" onclick="deleteUser('${u.userId}')" title="Delete user">
              <i class="fa-solid fa-trash"></i>
            </button>
          </div>
        </td>
      </tr>
    `;
  }).join("");
}

function filterUsersTable() {
  renderUsersTable();
}

function openCreateUserModal() {
  document.getElementById("modal-user-title").textContent = "Add New User Account";
  document.getElementById("user-form-id").value = "";
  document.getElementById("user-form-name").value = "";
  document.getElementById("user-form-email").value = "";
  document.getElementById("user-form-password").value = "";
  document.getElementById("user-form-password").placeholder = "Required for new user (e.g. Pass123)";
  document.getElementById("user-form-role").value = "VOLUNTEER";
  document.getElementById("user-form-phone").value = "+92 300 ";
  document.getElementById("user-form-city").value = "Karachi";
  document.getElementById("user-form-cnic").value = "";
  document.getElementById("user-form-skills").value = "";
  document.getElementById("user-form-status").value = "ACTIVE";
  document.getElementById("btn-save-user").textContent = "Create User Account";
  openModal("modal-user");
}

function openEditUserModal(userId) {
  const user = store.users.find(u => u.userId === userId);
  if (!user) return;

  document.getElementById("modal-user-title").textContent = "Edit User Account";
  document.getElementById("user-form-id").value = user.userId;
  document.getElementById("user-form-name").value = user.fullName || "";
  document.getElementById("user-form-email").value = user.email || "";
  document.getElementById("user-form-password").value = "";
  document.getElementById("user-form-password").placeholder = "Leave blank to preserve current password";
  document.getElementById("user-form-role").value = user.role || "VOLUNTEER";
  document.getElementById("user-form-phone").value = user.phone || "";
  document.getElementById("user-form-city").value = user.city || "Karachi";
  document.getElementById("user-form-cnic").value = user.cnic || "";
  document.getElementById("user-form-skills").value = (user.skills || []).join(", ");
  document.getElementById("user-form-status").value = user.status || "ACTIVE";
  document.getElementById("btn-save-user").textContent = "Save Changes";
  openModal("modal-user");
}

function handleSaveUser(event) {
  event.preventDefault();

  const userId = document.getElementById("user-form-id").value;
  const isEdit = !!userId;
  const name = document.getElementById("user-form-name").value.trim();
  const email = document.getElementById("user-form-email").value.trim();
  const password = document.getElementById("user-form-password").value.trim();
  const role = document.getElementById("user-form-role").value;
  const phone = document.getElementById("user-form-phone").value.trim();
  const city = document.getElementById("user-form-city").value;
  const cnic = document.getElementById("user-form-cnic").value.trim();
  const status = document.getElementById("user-form-status").value;
  const skillsInput = document.getElementById("user-form-skills").value.trim();
  const skills = skillsInput ? skillsInput.split(",").map(s => s.trim()).filter(Boolean) : [];

  if (isEdit) {
    const existing = store.users.find(u => u.userId === userId);
    if (existing) {
      existing.fullName = name;
      existing.email = email;
      existing.role = role;
      existing.phone = phone;
      existing.city = city;
      existing.cnic = cnic;
      existing.status = status;
      existing.skills = skills;
      if (password) existing.password = password;

      // Update volunteer entry if exists
      const volEntry = store.volunteers.find(v => v.userId === userId || v.email === existing.email);
      if (volEntry) {
        volEntry.fullName = name;
        volEntry.phone = phone;
        volEntry.email = email;
        volEntry.city = city;
        volEntry.skills = skills;
      }

      // If updating active session user
      if (store.session && store.session.userId === userId) {
        store.session.fullName = name;
        store.session.email = email;
        store.session.role = role;
        updateAuthUI();
      }
    }
    store.save();
    closeModal("modal-user");
    renderUsersTable();
    renderVolunteersTable();
    showToast(`User account "${name}" updated successfully!`, "success");
  } else {
    // Prevent duplicate email
    if (store.users.some(u => u.email.toLowerCase() === email.toLowerCase())) {
      showToast("A user with this email address already exists.", "danger");
      return;
    }

    const newUser = {
      userId: `usr_${Date.now()}`,
      fullName: name,
      email: email,
      password: password || "password123",
      role: role,
      phone: phone,
      city: city,
      cnic: cnic || "42101-*******-0",
      status: status,
      joinedDate: new Date().toLocaleDateString("en-US", { month: "short", day: "2-digit", year: "numeric" }),
      skills: skills
    };

    store.users.unshift(newUser);

    if (role === "VOLUNTEER") {
      store.volunteers.unshift({
        userId: newUser.userId,
        fullName: newUser.fullName,
        phone: newUser.phone,
        email: newUser.email,
        city: newUser.city,
        cnicMasked: newUser.cnic,
        skills: newUser.skills,
        interests: ["Community Volunteering"],
        completedEventsCount: 0,
        hoursVolunteered: 0,
        activeApplicationsCount: 0,
        status: "VERIFIED"
      });
      renderVolunteersTable();
    }

    store.save();
    closeModal("modal-user");
    renderUsersTable();
    updateBadges();
    updateOverviewStats();
    showToast(`New user "${name}" registered successfully!`, "success");
  }
}

function toggleUserStatus(userId) {
  const user = store.users.find(u => u.userId === userId);
  if (!user) return;

  if (store.session && store.session.userId === userId) {
    showToast("You cannot suspend your own active administrator account.", "warning");
    return;
  }

  user.status = user.status === "ACTIVE" ? "SUSPENDED" : "ACTIVE";
  store.save();
  renderUsersTable();
  showToast(`User ${user.fullName} is now ${user.status}.`, "info");
}

function deleteUser(userId) {
  if (store.session && store.session.userId === userId) {
    showToast("You cannot delete your own active administrator account.", "danger");
    return;
  }

  const user = store.users.find(u => u.userId === userId);
  if (!user) return;

  if (!confirm(`Are you sure you want to permanently delete user "${user.fullName}"?`)) return;

  store.users = store.users.filter(u => u.userId !== userId);
  store.volunteers = store.volunteers.filter(v => v.userId !== userId && v.email !== user.email);
  store.save();

  renderUsersTable();
  renderVolunteersTable();
  updateBadges();
  updateOverviewStats();
  showToast(`User account deleted successfully.`, "danger");
}

// 8.6 AUTHENTICATION & LOGIN FLOW
function checkAuthSession() {
  const overlay = document.getElementById("auth-overlay");
  if (!store.session) {
    overlay?.classList.remove("hidden");
  } else {
    overlay?.classList.add("hidden");
    updateAuthUI();
  }
}

function switchAuthTab(tab) {
  const signinForm = document.getElementById("form-signin");
  const signupForm = document.getElementById("form-signup");
  const tabSignin = document.getElementById("tab-btn-signin");
  const tabSignup = document.getElementById("tab-btn-signup");

  if (tab === "signin") {
    signinForm.style.display = "block";
    signupForm.style.display = "none";
    tabSignin.classList.add("active");
    tabSignup.classList.remove("active");
  } else {
    signinForm.style.display = "none";
    signupForm.style.display = "block";
    tabSignin.classList.remove("active");
    tabSignup.classList.add("active");
  }
}

function fillDemoLogin(role) {
  const emailInput = document.getElementById("signin-email");
  const passwordInput = document.getElementById("signin-password");

  if (role === "admin") {
    emailInput.value = "admin@volunteerconnect.org";
    passwordInput.value = "admin";
  } else if (role === "org") {
    emailInput.value = "coordinator@greenfuture.org";
    passwordInput.value = "org";
  } else if (role === "vol") {
    emailInput.value = "ahmed.khan@connect.org";
    passwordInput.value = "user";
  }
  showToast(`Pre-filled demo credentials for ${role.toUpperCase()}. Click Sign In to proceed.`, "info");
}

function handleSignIn(event) {
  event.preventDefault();
  const email = document.getElementById("signin-email").value.trim();
  const password = document.getElementById("signin-password").value.trim();

  let user = store.users.find(u => u.email.toLowerCase() === email.toLowerCase());

  if (!user && email === "admin@volunteerconnect.org") {
    user = DEFAULT_USERS[0];
  }

  if (!user) {
    showToast("Invalid credentials. Please register or use one of the quick demo buttons.", "danger");
    return;
  }

  if (user.status === "SUSPENDED") {
    showToast("This account is suspended. Contact system administrator.", "danger");
    return;
  }

  // Create active session
  store.session = {
    userId: user.userId,
    fullName: user.fullName,
    email: user.email,
    role: user.role,
    city: user.city
  };
  store.save();

  updateAuthUI();
  document.getElementById("auth-overlay")?.classList.add("hidden");
  showToast(`Welcome back, ${user.fullName}!`, "success");
}

function handleSignUp(event) {
  event.preventDefault();
  const name = document.getElementById("signup-name").value.trim();
  const email = document.getElementById("signup-email").value.trim();
  const role = document.getElementById("signup-role").value;
  const phone = document.getElementById("signup-phone").value.trim();
  const city = document.getElementById("signup-city").value;
  const cnic = document.getElementById("signup-cnic").value.trim();
  const password = document.getElementById("signup-password").value.trim();

  if (store.users.some(u => u.email.toLowerCase() === email.toLowerCase())) {
    showToast("An account with this email already exists. Please sign in.", "danger");
    return;
  }

  const newUser = {
    userId: `usr_${Date.now()}`,
    fullName: name,
    email: email,
    password: password,
    role: role,
    phone: phone,
    city: city,
    cnic: cnic || "42101-*******-5",
    status: "ACTIVE",
    joinedDate: new Date().toLocaleDateString("en-US", { month: "short", day: "2-digit", year: "numeric" }),
    skills: ["General Volunteering", "Community Support"]
  };

  store.users.unshift(newUser);

  if (role === "VOLUNTEER") {
    store.volunteers.unshift({
      userId: newUser.userId,
      fullName: newUser.fullName,
      phone: newUser.phone,
      email: newUser.email,
      city: newUser.city,
      cnicMasked: newUser.cnic,
      skills: newUser.skills,
      interests: ["Community Service"],
      completedEventsCount: 0,
      hoursVolunteered: 0,
      activeApplicationsCount: 0,
      status: "VERIFIED"
    });
  }

  // Create session
  store.session = {
    userId: newUser.userId,
    fullName: newUser.fullName,
    email: newUser.email,
    role: newUser.role,
    city: newUser.city
  };
  store.save();

  updateAuthUI();
  renderUsersTable();
  renderVolunteersTable();
  updateBadges();
  updateOverviewStats();

  document.getElementById("auth-overlay")?.classList.add("hidden");
  showToast(`Account created successfully! Welcome, ${name}!`, "success");
}

function handleLogout() {
  store.session = null;
  store.save();
  const overlay = document.getElementById("auth-overlay");
  overlay?.classList.remove("hidden");
  switchAuthTab("signin");
  showToast("You have been signed out from the portal.", "info");
}

function updateAuthUI() {
  if (!store.session) return;
  const user = store.session;
  const initials = (user.fullName || "AD").split(" ").map(w => w.charAt(0)).join("").substring(0, 2).toUpperCase();

  const roleDisplay = {
    SUPER_ADMIN: "Super Administrator",
    ORGANIZATION: "NGO Coordinator",
    VOLUNTEER: "Volunteer Lead"
  }[user.role] || user.role;

  // Sidebar elements
  const sidebarAvatar = document.getElementById("sidebar-user-avatar");
  const sidebarName = document.getElementById("sidebar-user-name");
  const sidebarRole = document.getElementById("sidebar-user-role");
  if (sidebarAvatar) sidebarAvatar.textContent = initials;
  if (sidebarName) sidebarName.textContent = user.fullName;
  if (sidebarRole) sidebarRole.innerHTML = `<span class="status-dot"></span> ${roleDisplay}`;

  // Topbar elements
  const topbarAvatar = document.getElementById("topbar-avatar");
  const topbarName = document.getElementById("topbar-user-name");
  const topbarRole = document.getElementById("topbar-user-role");
  if (topbarAvatar) topbarAvatar.textContent = initials;
  if (topbarName) topbarName.textContent = user.fullName;
  if (topbarRole) topbarRole.textContent = roleDisplay;
}

// 9. EXPORT PLATFORM DATA
function exportData(type) {
  let content = "";
  let filename = "";

  if (type === "events") {
    const headers = ["EventID", "Title", "Organization", "Category", "City", "Date", "Capacity", "Accepted", "Status"];
    const rows = store.events.map(e => [e.eventId, `"${e.title}"`, `"${e.organizationName}"`, e.category, e.city, e.dateText, e.capacity, e.volunteersAcceptedCount, e.status]);
    content = [headers.join(","), ...rows.map(r => r.join(","))].join("\n");
    filename = "volunteer_connect_events.csv";
  } else if (type === "volunteers") {
    const headers = ["UserID", "FullName", "Email", "Phone", "City", "CNIC_Masked", "HoursVolunteered", "EventsCount"];
    const rows = store.volunteers.map(v => [v.userId, `"${v.fullName}"`, v.email, v.phone, v.city, v.cnicMasked, v.hoursVolunteered, v.completedEventsCount]);
    content = [headers.join(","), ...rows.map(r => r.join(","))].join("\n");
    filename = "volunteer_connect_volunteers.csv";
  } else if (type === "organizations") {
    const headers = ["OrgID", "Name", "City", "Email", "Phone", "Status", "EventsCount"];
    const rows = store.orgs.map(o => [o.organizationId, `"${o.name}"`, o.city, o.contactEmail, o.contactPhone, o.verificationStatus, o.totalEventsCount]);
    content = [headers.join(","), ...rows.map(r => r.join(","))].join("\n");
    filename = "volunteer_connect_organizations.csv";
  } else if (type === "users") {
    const headers = ["UserID", "FullName", "Email", "Role", "Phone", "City", "CNIC", "Status", "JoinedDate"];
    const rows = store.users.map(u => [u.userId, `"${u.fullName}"`, u.email, u.role, u.phone, u.city, u.cnic, u.status, u.joinedDate]);
    content = [headers.join(","), ...rows.map(r => r.join(","))].join("\n");
    filename = "volunteer_connect_users.csv";
  } else if (type === "applications") {
    const headers = ["AppID", "EventTitle", "Organization", "VolunteerName", "AppliedDate", "Status"];
    const rows = store.applications.map(a => [a.applicationId, `"${a.eventTitle}"`, `"${a.organizationName}"`, `"${a.volunteerName}"`, a.appliedAtText, a.status]);
    content = [headers.join(","), ...rows.map(r => r.join(","))].join("\n");
    filename = "volunteer_connect_applications.csv";
  } else if (type === "json") {
    content = JSON.stringify({
      events: store.events,
      users: store.users,
      organizations: store.orgs,
      volunteers: store.volunteers,
      applications: store.applications,
      reports: store.reports,
      exportedAt: new Date().toISOString()
    }, null, 2);
    filename = "volunteer_connect_backup.json";
  }

  const blob = new Blob([content], { type: type === "json" ? "application/json" : "text/csv;charset=utf-8;" });
  const url = URL.createObjectURL(blob);
  const link = document.createElement("a");
  link.setAttribute("href", url);
  link.setAttribute("download", filename);
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);

  showToast(`Exported ${filename}`, "success");
}

// 10. FIREBASE & GOVERNANCE SETTINGS
function toggleFirebaseMode() {
  const btn = document.getElementById("btn-toggle-firebase");
  const indicator = document.getElementById("sync-status-indicator");
  
  if (btn.textContent.includes("Firebase")) {
    btn.textContent = "Switch to Local Mode";
    indicator.className = "data-mode-badge";
    indicator.style.color = "#EA4335";
    indicator.style.borderColor = "#EA4335";
    indicator.style.background = "#FEF2F2";
    indicator.innerHTML = `<i class="fa-brands fa-google"></i> <span>Firebase Mode</span>`;
    showToast("Switched to Firebase Live Connector Mode", "info");
  } else {
    btn.textContent = "Switch to Firebase Mode";
    indicator.className = "data-mode-badge";
    indicator.style.color = "var(--success)";
    indicator.style.borderColor = "rgba(16, 185, 129, 0.2)";
    indicator.style.background = "var(--success-light)";
    indicator.innerHTML = `<i class="fa-solid fa-circle-check"></i> <span>Local Synced State</span>`;
    showToast("Switched to Local Synchronized Mode", "info");
  }
}

function saveFirebaseConfig() {
  const pid = document.getElementById("fb-project-id").value;
  const key = document.getElementById("fb-api-key").value;
  localStorage.setItem("vc_fb_pid", pid);
  localStorage.setItem("vc_fb_key", key);
  showToast("Firebase parameters saved successfully!", "success");
}

function saveGovernanceRules() {
  const maxApps = document.getElementById("cfg-max-apps").value;
  const minAge = document.getElementById("cfg-min-age").value;
  localStorage.setItem("vc_cfg_max_apps", maxApps);
  localStorage.setItem("vc_cfg_min_age", minAge);
  showToast(`Governance rules saved: Max ${maxApps} active applications enforced.`, "success");
}

// Modal Helpers
function openModal(id) {
  document.getElementById(id)?.classList.add("active");
}

function closeModal(id) {
  document.getElementById(id)?.classList.remove("active");
}

function openBroadcastModal() {
  switchView("broadcast");
}

// Toast Notifications
function showToast(message, type = "info") {
  const container = document.getElementById("toast-container");
  if (!container) return;

  const toast = document.createElement("div");
  toast.className = `toast toast-${type}`;
  
  let icon = "fa-circle-info";
  if (type === "success") icon = "fa-circle-check";
  if (type === "danger") icon = "fa-circle-exclamation";
  if (type === "warning") icon = "fa-triangle-exclamation";

  toast.innerHTML = `
    <i class="fa-solid ${icon}"></i>
    <span>${escapeHtml(message)}</span>
  `;

  container.appendChild(toast);

  setTimeout(() => {
    toast.style.opacity = "0";
    toast.style.transform = "translateY(12px)";
    toast.style.transition = "all 0.3s ease";
    setTimeout(() => toast.remove(), 300);
  }, 3500);
}

// Utility: Escape HTML to avoid XSS
function escapeHtml(str) {
  if (!str) return "";
  return String(str)
    .replace(/&/g, "&amp;")
    .replace(/</g, "&lt;")
    .replace(/>/g, "&gt;")
    .replace(/"/g, "&quot;")
    .replace(/'/g, "&#039;");
}
