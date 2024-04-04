import Dashboard from "./views/Dashboard.js";
import Profile from "views/examples/Profile.js";
import Maps from "views/examples/Maps.js";
import Register from "views/examples/Register.js";
import Login from "views/examples/Login.js";
import Bookings from "./views/examples/Bookings.js";
import Icons from "views/examples/Icons.js";
import Ambulance from "./views/examples/Ambulance";
import Users from "./views/examples/Users";

var routes = [
  {
    path: "/index",
    name: "Dashboard",
    icon: "ni ni-tv-2 text-primary",
    component: <Dashboard />,
    layout: "/admin",
  },
  {
    path: "/ambulance",
    name: "Ambulance",
    icon: "ni ni-pin-3 text-orange",
    component: <Ambulance />,
    layout: "/admin",
  },
  {
    path: "/bookings",
    name: "Bookings",
    icon: "ni ni-bullet-list-67 text-red",
    component: <Bookings />,
    layout: "/admin",
  },

  {
    path: "/users",
    name: "User",
    icon: "ni ni-single-02 text-yellow",
    component: <Users />,
    layout: "/admin",
  },
  // {
  //   path: "/user-profile",
  //   name: "User Profile",
  //   icon: "ni ni-single-02 text-yellow",
  //   component: <Profile />,
  //   layout: "/admin",
  // },
];
export default routes;


// {
//   path: "/icons",
//   name: "Icons",
//   icon: "ni ni-planet text-blue",
//   component: <Icons />,
//   layout: "/admin",
// },
// {
//   path: "/maps",
//   name: "Maps",
//   icon: "ni ni-pin-3 text-orange",
//   component: <Maps />,
//   layout: "/admin",
// },