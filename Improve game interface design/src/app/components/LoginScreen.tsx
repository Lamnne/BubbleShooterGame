import { useState } from 'react';
import { motion } from 'motion/react';

interface LoginScreenProps {
  onLogin: (username: string) => void;
}

export default function LoginScreen({ onLogin }: LoginScreenProps) {
  const [username, setUsername] = useState('');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (username.trim()) {
      onLogin(username);
    }
  };

  return (
    <motion.div
      initial={{ opacity: 0, scale: 0.8 }}
      animate={{ opacity: 1, scale: 1 }}
      exit={{ opacity: 0, scale: 0.8 }}
      transition={{ duration: 0.5, ease: "easeOut" }}
      className="flex flex-col items-center justify-center gap-12 p-8"
    >
      <motion.h1
        initial={{ y: -50, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ delay: 0.2, duration: 0.6 }}
        className="text-8xl text-white tracking-wider font-black"
        style={{
          textShadow: '0 0 30px rgba(59, 130, 246, 0.8), 0 0 60px rgba(147, 51, 234, 0.6)',
        }}
      >
        LOGIN
      </motion.h1>

      <motion.form
        onSubmit={handleSubmit}
        initial={{ y: 50, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ delay: 0.4, duration: 0.6 }}
        className="flex flex-col gap-6 w-96"
      >
        <div className="flex flex-col gap-3">
          <label className="text-white text-xl tracking-wide">
            Enter Username:
          </label>
          <motion.input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            className="px-6 py-4 bg-gradient-to-r from-blue-900/50 to-purple-900/50 backdrop-blur-md border-4 border-cyan-400 rounded-xl text-white text-xl placeholder-gray-400 outline-none focus:border-cyan-300 focus:shadow-[0_0_30px_rgba(34,211,238,0.6)] transition-all"
            placeholder="Your name..."
            whileFocus={{ scale: 1.02 }}
            autoFocus
          />
        </div>

        <motion.button
          type="submit"
          className="px-8 py-4 bg-gradient-to-r from-green-500 to-emerald-600 text-white text-2xl font-black tracking-wider rounded-xl shadow-lg hover:shadow-[0_0_30px_rgba(34,197,94,0.6)] transition-all border-4 border-green-300"
          whileHover={{ scale: 1.05, y: -2 }}
          whileTap={{ scale: 0.98 }}
          disabled={!username.trim()}
        >
          START GAME
        </motion.button>
      </motion.form>

      <motion.div
        className="flex gap-4 mt-8"
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 0.8 }}
      >
        {[...Array(5)].map((_, i) => (
          <motion.div
            key={i}
            className="w-12 h-12 rounded-full"
            style={{
              background: ['#ef4444', '#3b82f6', '#22c55e', '#eab308', '#8b5cf6'][i],
              boxShadow: '0 0 20px rgba(0,0,0,0.3)',
            }}
            animate={{
              y: [0, -20, 0],
              scale: [1, 1.1, 1],
            }}
            transition={{
              duration: 2,
              repeat: Infinity,
              delay: i * 0.2,
            }}
          />
        ))}
      </motion.div>
    </motion.div>
  );
}
